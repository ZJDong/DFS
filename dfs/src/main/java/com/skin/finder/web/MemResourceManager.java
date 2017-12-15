package com.skin.finder.web;

import java.util.HashMap;

import com.skin.finder.ContentEntry;

/**
 * <p>Title: MemResourceManager</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class MemResourceManager extends ResourceManager {
    /**
     * @param file
     * @param home
     */
    public MemResourceManager(String file, String home) {
        super(file, home);
        this.cache = new HashMap<String, ContentEntry>(512);
    }
}

