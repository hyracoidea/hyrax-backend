package com.hyrax.microservice.sample.rest.api.helper;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.CharStreams;
import com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class JsonFileReader {

    public static String read(final String folder, final String filename) {
        final String resourceName = folder + File.separator + filename;
        final URL url = Resources.getResource(resourceName);
        try {
            final CharSource charSource = Resources.asCharSource(url, Charsets.UTF_8);
            return CharStreams.toString(charSource.openBufferedStream());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
