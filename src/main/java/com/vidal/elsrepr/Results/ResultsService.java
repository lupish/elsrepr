package com.vidal.elsrepr.Results;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResultsService {

    public String ping() {
        return "pong";
    }

    public List<Path> searchFilesWithExtension(String rootDirectory, String extension) throws IOException {
        Path rootPath = Paths.get(rootDirectory);
        return searchFilesWithExtension(rootPath, extension);
    }

    private List<Path> searchFilesWithExtension(Path rootPath, String extension) throws IOException {
        List<Path> result = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(rootPath)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    result.addAll(searchFilesWithExtension(entry, extension)); // Recursivamente buscar en subcarpetas
                } else if (entry.getFileName().toString().endsWith("." + extension)) {
                    result.add(entry); // Archivo con la extensión deseada encontrado
                }
            }
        }

        return result;
    }

    public Double getRecoveryTarget(String fileName) {
        String target = "0";

        Pattern p = Pattern.compile("tasas(\\d+)_");
        Matcher matcher = p.matcher(fileName);
        // obtener primera coincidencia
        if (matcher.find()) {
            target = matcher.group(1);
            if (target.equals("05")) {
                target = "0.5";
            } 
            if (target.equals("10")) {
                target = "1";
            }
        }

        return Double.parseDouble(target);
    }

    public String getVersion(String fileName) {
        String target = "1";

        Pattern p = Pattern.compile("v(\\d+)_");
        Matcher matcher = p.matcher(fileName);
        // obtener primera coincidencia
        if (matcher.find()) {
            target = matcher.group(1);
        }

        return "TSv" + target;
    }

    public List<ResultsResponse> getFile(String folderPath, String fileName) {
        List<ResultsResponse> resultList = new ArrayList<>();
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.equals(fileName);
                }
            });

            if (files != null) {
                for (File f : files) {
                    Date creationDate = new java.util.Date(f.lastModified());
                    resultList.add(new ResultsResponse(f.getName(), creationDate, getVersion(f.getName()), getRecoveryTarget(f.getName())));
                }
            }
        }

        return resultList;
    }

    public List<ResultsResponse> listFiles(String folderPath, String ext) {
        List<ResultsResponse> resultList = new ArrayList<>();

        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(ext);
                }
            }); 

            if (files != null) {
                for (File f : files) {
                    Date creationDate = new java.util.Date(f.lastModified());
                    resultList.add(new ResultsResponse(f.getName(), creationDate, getVersion(f.getName()), getRecoveryTarget(f.getName())));
                }
            }

            
        } else {
            System.out.println("Do not exist folder " + folderPath);
        }

        return resultList;
    }

    public List<ResultsResponse> listResults(@RequestBody ResultsParameters p) {
        
        if (p.getArchivo().length() == 0) {
            return listFiles(p.getCarpeta(), "out");
        } else {
            return getFile(p.getCarpeta(), p.getArchivo());
        }

    }
    
    public Map<String, List<ResultResponseDetail>> readFile(String folderPath, String fileName) {
        Map<String, List<ResultResponseDetail>> mapCostos = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(folderPath+fileName));
            String linea, tipoCosto;
            Matcher matcher;
            Pattern patronArchivoConfig = Pattern.compile(".*_(.*).dat");
            Pattern patronCostos = Pattern.compile("\\s*(COSTOS|Plan de Manufacturación|Plan de Entrega|Plan de Remanufacturación) =\\s*(\\d+(\\.\\d+)?)");
            Pattern patronTiempo = Pattern.compile("TIEMPO \\(ms\\):\\s*(\\d+(\\.\\d+)?)");

            String clave = ""; 
            while ((linea = br.readLine()) != null) {
                // System.out.println(linea);

                if (linea.contains(".dat")) {
                    //clave = linea;
                    matcher = patronArchivoConfig.matcher(linea);
                    if (matcher.find()) {
                        clave = matcher.group(1).trim();
                        mapCostos.put(clave, new ArrayList<>());
                    }
                }

                if (linea.contains("TIEMPO")) {
                    matcher = patronTiempo.matcher(linea);
                    if (matcher.find()) {
                        tipoCosto = "runtimeMS";
                        String numero = matcher.group(1);
                        mapCostos.get(clave).add(new ResultResponseDetail(tipoCosto, Double.parseDouble(numero)));
                    }
                    
                }

                matcher = patronCostos.matcher(linea);
                if (matcher.find()) {
                    String patron = matcher.group(1).trim(); // Elimina espacios en blanco al principio y al final
                    String numero = matcher.group(2);

                    tipoCosto = "";
                    switch (patron) {
                        case "COSTOS":
                            tipoCosto = "costoTotal";
                            break;
                        case "Plan de Entrega":
                            tipoCosto = "Entrega";
                            break;
                        case "Plan de Manufacturación":
                            tipoCosto = "Manu";
                            break;
                        case "Plan de Remanufacturación":
                            tipoCosto = "Remanu";
                            break;
                        default:
                            tipoCosto = patron;
                            break;
                    }
                    mapCostos.get(clave).add(new ResultResponseDetail(tipoCosto, Double.parseDouble(numero)));
                }
            }

            // Recorrer y realizar impresiones
            /*for (Map.Entry<String, List<ResultResponseDetail>> entry : mapCostos.entrySet()) {
                String clave2 = entry.getKey();
                List<ResultResponseDetail> valores = entry.getValue();

                System.out.println("Clave: " + clave2);
                Double total = 0.0;
                for (ResultResponseDetail elemento : valores) {
                    System.out.println("  " + elemento);
                    total = total + elemento.getValor();
                }
                System.out.print(total);
            }*/

            br.close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        return mapCostos;
    }
}
