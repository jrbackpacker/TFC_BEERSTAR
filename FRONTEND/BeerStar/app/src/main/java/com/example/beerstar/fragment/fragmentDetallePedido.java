package com.example.beerstar.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.beerstar.R;
import com.example.beerstar.response.responseProductos;
import com.example.beerstar.response.responseUsuario;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class fragmentDetallePedido extends Fragment {

    private TextView textUsuario, textFecha, textArticulos, textTotal, textResumen;
    private Button buttonProcederPago, buttonAbrirPdf;

    private File pdfFile;

    public static fragmentDetallePedido newInstance(List<responseProductos> articulos, responseUsuario usuario) {
        fragmentDetallePedido fragment = new fragmentDetallePedido();
        Bundle args = new Bundle();
        args.putSerializable("articulos", (java.io.Serializable) articulos);
        args.putSerializable("usuario", usuario);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_pedido, container, false);

        textUsuario = view.findViewById(R.id.textUsuario);
        textFecha = view.findViewById(R.id.textFecha);
        textArticulos = view.findViewById(R.id.textArticulos);
        textTotal = view.findViewById(R.id.textTotal);
        textResumen = view.findViewById(R.id.textResumen);
        buttonProcederPago = view.findViewById(R.id.buttonProcederPago);
        buttonAbrirPdf = view.findViewById(R.id.buttonAbrirPdf);

        List<responseProductos> articulos = (List<responseProductos>) getArguments().getSerializable("articulos");
        responseUsuario usuario = (responseUsuario) getArguments().getSerializable("usuario");

        textUsuario.setText("Usuario: " + usuario.getMensaje());
        textFecha.setText("Fecha: " + DateFormat.getDateTimeInstance().format(new Date()));

        // Artículos
        StringBuilder articulosStr = new StringBuilder();
        double subtotal = 0;
        for (responseProductos articulo : articulos) {
            articulosStr.append(articulo.getNombre())
                    .append("\nCantidad: ").append(articulo.getCantidad())
                    .append("\nPrecio: $").append(String.format("%.2f", articulo.getPrecio()))
                    .append("\n\n");
            subtotal += articulo.getPrecio() * articulo.getCantidad();
        }

        double iva = subtotal * 0.21;
        double totalConIva = subtotal + iva;

        textArticulos.setText(articulosStr.toString());

        String totalStr = "Subtotal (sin IVA): $" + String.format("%.2f", subtotal)
                + "\nIVA (21%): $" + String.format("%.2f", iva)
                + "\nTotal (con IVA): $" + String.format("%.2f", totalConIva);

        textTotal.setText(totalStr);
        textResumen.setText("Gracias por tu compra. El pedido será procesado y enviado a la brevedad.");

        buttonProcederPago.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Compra finalizada. ¡Gracias!", Toast.LENGTH_SHORT).show();
        });

        double finalSubtotal = subtotal;
        buttonAbrirPdf.setOnClickListener(v -> {
            generarPdf(usuario, articulos, finalSubtotal, iva, totalConIva);
            abrirPdf();
        });

        return view;
    }

    private void generarPdf(responseUsuario usuario, List<responseProductos> articulos, double subtotal, double iva, double total) {
        try {
            File directorio = new File(requireContext().getExternalFilesDir(null), "facturas");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            pdfFile = new File(directorio, "factura_pedido.pdf");

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

            document.add(new Paragraph("Usuario: " + usuario.getMensaje()));
            document.add(new Paragraph("Fecha: " + DateFormat.getDateTimeInstance().format(new Date())));
            document.add(new Paragraph("\nArtículos:"));

            for (responseProductos articulo : articulos) {
                document.add(new Paragraph(
                        articulo.getNombre() + "\nCantidad: " + articulo.getCantidad() +
                                "\nPrecio: $" + String.format("%.2f", articulo.getPrecio()) + "\n"
                ));
            }

            document.add(new Paragraph("\nSubtotal (sin IVA): $" + String.format("%.2f", subtotal)));
            document.add(new Paragraph("IVA (21%): $" + String.format("%.2f", iva)));
            document.add(new Paragraph("Total (con IVA): $" + String.format("%.2f", total)));
            document.add(new Paragraph("\nGracias por tu compra."));

            document.close();
            Toast.makeText(getContext(), "PDF generado correctamente", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(getContext(), "Error generando PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void abrirPdf() {
        if (pdfFile != null && pdfFile.exists()) {
            Uri uri = FileProvider.getUriForFile(
                    requireContext(),
                    requireContext().getPackageName() + ".provider",
                    pdfFile
            );

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                Intent chooser = Intent.createChooser(intent, "Abrir con...");
                startActivity(chooser);
            } catch (Exception e) {
                Toast.makeText(getContext(), "No hay una aplicación para abrir PDFs", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "El archivo PDF no se ha generado aún", Toast.LENGTH_SHORT).show();
        }
    }
}
