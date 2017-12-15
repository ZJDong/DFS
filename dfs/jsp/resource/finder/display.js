/*
 * $RCSfile: finder.js,v $
 * $Revision: 1.1 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
jQuery(function() {
    var setOptions = function(e, list, value) {
        for(var i = e.length - 1; i > -1; i--) {
            e.options.remove(i);
        }

        var flag = false;
        var selected = value;

        if(selected == null || selected == undefined) {
            selected = e.getAttribute("selected-value");
        }

        for(var i = 0; i < list.length; i++) {
            var item = list[i];
            var option = new Option(item, item);

            if(selected != null && item == selected) {
                option.selected = true;
            }
            e.options.add(option);
        }
    };

    var themes = ["Default", "Django", "Eclipse", "Emacs", "FadeToGrey", "MDUltra", "Midnight", "RDark"];
    var types = ["", "as", "sh", "bsh", "bash", "log", "shell", "cpp", "cs", "css", "dpi", "diff", "erl", "erlang", "groovy", "java", "js", "pl", "php", "txt", "text", "py", "ruby", "sass", "scala", "sql", "vb", "vbs", "xml", "xhtml", "xslt", "html", "htm", "asp", "jsp", "jspf", "asp", "php"];
    var encodings = ["utf-8", "gbk", "gb2312", "iso-8859-1"];

    setOptions(document.getElementById("uiThemeOption"), themes);
    setOptions(document.getElementById("uiTypeOption"), types);
    setOptions(document.getElementById("uiEncodingOption"), encodings);
});

jQuery(function() {
    var map = {
        "??": "brush: bash;",
        "as": "brush: actionscript3;",
        "bsh": "brush: bash;",
        "log": "brush: bash;",
        "cpp": "brush: cpp;",
        "cs": "brush: cs;",
        "css": "brush: css;",
        "dhi": "brush: dpi;",
        "diff": "brush: diff;",
        "erl": "brush: erl;",
        "erlang": "brush: erlang;",
        "groovy": "brush: groovy;",
        "java": "brush: java;",
        "js": "brush: javascript;",
        "pl": "brush: perl;",
        "php": "brush: php;",
        "plain": "brush: plain;",
        "sh": "brush: bash;",
        "py": "brush: python;",
        "ruby": "brush: ruby;",
        "sass": "brush: sass;",
        "scala": "brush: scala;",
        "sql": "brush: sql;",
        "vb": "brush: vbscript;",
        "vbs": "brush: vbscript;",
        "xml": "brush: xml;",
        "xhtml": "brush: xml;",
        "xslt": "brush: xml;",
        "html": "brush: xml;",
        "htm": "brush: xml;",
        "jsp": "brush: xml;",
        "jspf": "brush: xml;",
        "asp": "brush: xml;",
        "php": "brush: xml;"
    };

    var type = jQuery("#content").attr("file-type");

    if(type == "??") {
        jQuery("#content pre").attr("class", "brush: bash;");
    }
    else {
        var brush = map[type];

        if(type != null) {
            jQuery("#content pre").attr("class", brush);
        }
        else {
            jQuery("#content pre").attr("class", "brush: plain;");
        }
    }
});

jQuery(function() {
    function path() {
        var result = [];
        var args = arguments;
        var requestURI = window.location.pathname;

        for(var i = 0; i < args.length; i++) {
            result.push(args[i].replace("@", requestURI + "?action=res&path=/sh/"));
        }
        return result;
    };

    var args = path(
        "applescript            @shBrushAppleScript.js",
        "actionscript3 as3      @shBrushAS3.js",
        "bash shell             @shBrushBash.js",
        "coldfusion cf          @shBrushColdFusion.js",
        "cpp c                  @shBrushCpp.js",
        "c# c-sharp csharp      @shBrushCSharp.js",
        "css                    @shBrushCss.js",
        "delphi pascal          @shBrushDelphi.js",
        "diff patch pas         @shBrushDiff.js",
        "erl erlang             @shBrushErlang.js",
        "groovy                 @shBrushGroovy.js",
        "java                   @shBrushJava.js",
        "jfx javafx             @shBrushJavaFX.js",
        "js jscript javascript  @shBrushJScript.js",
        "perl pl                @shBrushPerl.js",
        "php                    @shBrushPhp.js",
        "text plain             @shBrushPlain.js",
        "py python              @shBrushPython.js",
        "ruby rails ror rb      @shBrushRuby.js",
        "sass scss              @shBrushSass.js",
        "scala                  @shBrushScala.js",
        "sql                    @shBrushSql.js",
        "vb vbnet               @shBrushVb.js",
        "xml xhtml xslt html    @shBrushXml.js"
    );
    SyntaxHighlighter.autoloader.apply(null, args);
    SyntaxHighlighter.all();
});

jQuery(function() {
    jQuery(window).resize(function() {
        var c = jQuery("#content div.syntaxhighlighter");
        c.css("overflow", "auto");
        c.height(jQuery(window).height() - jQuery("#content").position().top);
    });

    setTimeout(function() {
        jQuery("#content").show();
        jQuery(window).resize();
        jQuery("#loading").remove();
    }, 500);
});
