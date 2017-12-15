/*
 * $RCSfile: Manifest.java,v $
 * $Revision: 1.1 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.finder.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 * <p>Title: Manifest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Manifest {
    private static final Properties properties = load();

    /**
     * @return String
     */
    public static String getVersion() {
        return getValue("Manifest-Version");
    }

    /**
     * @return String
     */
    public static String getCreated() {
        return getValue("Created-By");
    }

    /**
     * @return String
     */
    public static String getDeveloper() {
        return getValue("Developer");
    }

    /**
     * @return String
     */
    public static String getBuilt() {
        return getValue("Build-By");
    }

    /**
     * @return String
     */
    public static String getBuildType() {
        return getValue("Build-Type");
    }

    /**
     * @return String
     */
    public static String getBuildTime() {
        return getValue("Build-Time");
    }

    /**
     * @param name
     * @return String
     */
    public static String getValue(String name) {
        return properties.getProperty(name);
    }

    /**
     * @return Properties
     */
    public static Properties load() {
        Properties properties = new Properties();
        File jar = ClassUtil.getJarFile(Manifest.class);

        if(jar != null) {
            ClassLoader classLoader = Manifest.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("/META-INF/MANIFEST.MF");

            if(inputStream != null) {
                try {
                    load(new InputStreamReader(inputStream, "utf-8"), properties);
                }
                catch(Exception e) {
                }
                properties.setProperty("Build-Type", "SJ");
            }
            else {
                properties.setProperty("Build-Type", "UJ");
            }
        }
        else {
            properties.setProperty("Build-Type", "UC");
        }
        return properties;
    }

    /**
     * @param reader
     * @return Properties
     * @throws IOException
     */
    private static Properties load(Reader reader, Properties properties) throws IOException {
        String line = null;
        BufferedReader buffer = new BufferedReader(reader);

        while((line = buffer.readLine()) != null) {
            line = line.trim();

            if(line.length() < 1) {
                continue;
            }

            int i = line.indexOf(":");

            if(i > -1) {
                String name = line.substring(0, i).trim();
                String value = line.substring(i + 1).trim();

                if(name.length() > 0 && value.length() > 0) {
                    properties.setProperty(name, value);
                }
            }
        }
        return properties;
    }
}
