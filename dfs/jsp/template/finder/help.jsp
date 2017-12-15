<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>Finder - Powered by Finder</title>
<link rel="shortcut icon" href="?action=res&path=/finder/images/favicon.png"/>
<style type="text/css">
.red{color: #ff0000;}
div.wrap{margin: 0px auto; width: 980px;}
ul.list{list-style-type: disc;}
ul.list li{line-height: 24px; font-size: 13px;}
table tr.head td{font-size: 14px;}
table tr td{padding-left: 4px; height: 24px; font-size: 13px;}
</style>
</head>
<body>
<div class="wrap">
    <div class="finder">
        <div class="menu-bar" style="padding-top: 4px;">
            <div style="float: left; width: 80px;">
                <a class="button disabled" href="javascript:void(0)"><span class="back-disabled"></span></a>
                <a class="button" href="javascript:void(0)" onclick="window.location.reload();" title="刷新"><span class="refresh"></span></a>
            </div>
            <div style="float: left; height: 28px; position: relative;"></div>
            <div style="float: right; width: 40px;">
                <a class="button" href="${requestURI}?action=finder.help" title="帮助"><span class="help"></span></a>
            </div>
        </div>
    </div>
    <div class="finder">
        <h1>Finder简介</h1>
        <p>Finder是一个高性能的Web版分布式文件管理工具，它具有体积小，易部署，功能强大等特点。</p>
        <p>下载地址：<a href="http://www.finderweb.net" target="_blank">http://www.finderweb.net</a></p>
        <ul class="list">
            <li>(<span class="red">程序员专用</span>) 支持集群部署，允许你同时管理多台机器上的文件或者查看不同机器上的日志；</li>
            <li>(<span class="red">程序员专用</span>) grep支持，类似linux系统的grep命令，支持任意大小的文件，支持随时查看文件的任意位置，像播放器一样点击进度条的任意位置；</li>
            <li>(<span class="red">程序员专用</span>) less支持，类似linux系统的less命令，支持任意大小的文件，支持随时查看文件的任意位置，像播放器一样点击进度条的任意位置；</li>
            <li>(<span class="red">程序员专用</span>) tail支持，类似linux系统的tail命令，支持任意大小的文件；</li>
            <li><span class="red">支持细粒度的权限控制，能满足不同的权限需求；IT运维或者公司内部资料分享，允许控制文件可见和文件的各种操作。</span></li>
            <li>支持全键盘操作，几乎所有操作均有对应的快捷键支持；</li>
            <li>支持右键菜单，文件的常规操作都可以通过右键菜单完成；</li>
            <li>支持文件重命名，点击选中文件，然后按F2即可重命名文件；</li>
            <li>支持大文件上传，超大文件会自动分段上传，默认设置每次上传5M；</li>
            <li>支持文件拖拽上传，可同时拖拽多个文件上传；</li>
            <li>支持截图上传，截图之后按Ctrl + Shift + V；</li>
            <li>支持音频和视频播放（需支持H5的浏览器）；</li>
            <li>体积小，只有不到3M，目前一般基于SSH的web应用，基本都在几十兆左右。Finder除了日志组件无任何第三方依赖（日志组件也不需要单独安装）;</li>
            <li>无数据库设计，免去部署数据库的麻烦，所有数据存储都存在本地文件系统，集群环境下分布式存储。</li>
            <li>易于部署，直接扔到Tomcat里即可；</li>
            <li>基于web的文件管理，几乎所有的操作系统和服务器的防火墙默认都对HTTP开放，而FTP大多需要专门开通；不需要用户安装专门的客户端软件，使用浏览器即可。</li>
            <li>对网络环境无任何要求，不需要做任何特殊设置。出于安全考虑，几乎所有的服务器都限制单个HTTP请求体的大小，且默认值很小，一般在2M左右，并且限制连接时间。Finder不需要专门设置即可上传或者下载超大的文件，Finder所有的功能都使用短连接完成以避免服务器超时限制。对于大文件采用分片上传，一方面可以避免服务器的限制，另一方面在网络环境不好的情况下提高上传的成功率，因为大文件长时间连接一旦网络断掉就需要全部重传，Finder采用分片的方式，每次只上传一段数据，如果失败自动重新上传这一段，并且针对每一段都自动重试3次。</li>
        </ul>
    </div>

    <div class="finder">
        <h1>使用技巧</h1>
        <ul class="list">
            <li>文件列表页面 - 双击文件名可以打开文件。</li>
            <li>文件列表页面 - 按住Ctrl键点击tail或者less可以在新窗口打开less和tail。</li>
            <li>文件列表页面 - 按住Ctrl键点击刷新可在新窗口打开文件列表页。</li>
            <li>音频播放的同时可以继续其他操作，包括切换不同的文件夹。</li>
            <li>地址栏的suggest列表支持键盘上下键滚动。</li>
        </ul>
    </div>

    <div class="finder">
        <h1>剪切板</h1>
        <p>Finder中有两种剪切板，一种是操作系统的剪切板，由于浏览器安全限制，Finder无法直接访问操作系统的剪切板，只能间接访问，当用户在Finder的界面上使用CTRL + V的时候，Finder可以访问到系统剪切板中的截图并上传。Finder无法访问剪切板中的文件。</p>
        <p>第二种剪切板是Finder自己的剪切板，用户在界面上选择多个文件拷贝或者剪切的时候，Finder将需要拷贝的文件信息放到自己的剪切板中，在用户使用Finder自己的右键菜单上点击粘贴的时候Finder从自己的剪切板中读取文件信息完成拷贝或者粘贴。</p>
        <p>所以CTRL + V只能上传操作系统剪切板中的截图。</p>
    </div>

    <div class="finder">
        <h1>快捷键</h1>
        <table>
            <tr class="head">
                <td style="width: 480px; height: 30px; background-color: #efefef;">操作</td>
                <td style="width: 300px; height: 30px; background-color: #efefef;">快捷键</td>
            </tr>
            <tr>
                <td>重命名</td>
                <td>F2</td>
            </tr>
            <tr>
                <td>向上滚动</td>
                <td>UP</td>
            </tr>
            <tr>
                <td>向下滚动</td>
                <td>DOWN</td>
            </tr>
            <tr>
                <td>向左滚动</td>
                <td>LEFT</td>
            </tr>
            <tr>
                <td>向右滚动</td>
                <td>RIGHT</td>
            </tr>
            <tr>
                <td>全选</td>
                <td>CTRL + A</td>
            </tr>
            <tr>
                <td>剪切(Finder中的文件)</td>
                <td>CTRL + X</td>
            </tr>
            <tr>
                <td>拷贝(Finder中的文件)</td>
                <td>CTRL + C</td>
            </tr>
            <tr>
                <td>粘贴截图</td>
                <td>CTRL + V</td>
            </tr>
            <tr>
                <td>多选</td>
                <td>CTRL + CLICK</td>
            </tr>
            <tr>
                <td>多选</td>
                <td>ALT + CLICK</td>
            </tr>
            <tr>
                <td>多选</td>
                <td>CTRL + [UP|DOWN|LEFT|RIGHT]</td>
            </tr>
            <tr>
                <td>删除选中文件</td>
                <td>DELETE</td>
            </tr>
        </table>
    </div>
</div>
<div style="height: 200px;"></div>
</body>
</html>
