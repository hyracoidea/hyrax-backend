package com.hyrax.microservice.reader;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class JsonFileReader {

    public static String read(final String folder, final String filename) {
        final URL url = Resources.getResource(folder + File.separator + filename);
        try {
            return String.join(StringUtils.LF, Files.readLines(new File(url.getFile()), Charsets.UTF_8));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
