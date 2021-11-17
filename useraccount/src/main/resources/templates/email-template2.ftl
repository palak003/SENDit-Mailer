<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>SENDit Mailer Template</title>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="center" valign="top" bgcolor="#838383"
            style="background-color: #838383;"><br> <br>
            <table width="600" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center" valign="top" bgcolor="#d3be6c"
                        style="background-color: cyan; font-family: Arial, Helvetica, sans-serif; font-size: 13px; color: #000000; padding: 0px 15px 150px 15px;">
                        <br>
                        <div style="font-size: 48px; color:blue;">
                            <div id="headline">${Headline}
                            Template 2</div>
                            <style>
                                #headline{
                                    position: absolute;
                                    width: 494px;
                                    height: 75px;
                                    left: 522px;
                                    top: 28px;

                                    font-family: Roboto;
                                    font-style: normal;
                                    font-weight: bold;
                                    font-size: 33px;
                                    line-height: 75px;
                                    /* identical to box height */


                                    color: #4D53E9;
                                }
                            </style>
                        </div>

                        <div style="font-size: 24px; color: #E94D8E;">
                            <br><br>
                            <div id="tagline">${Tagline}</div>
                            <style>
                                #tagline{
                                    position: absolute;
                                    width: 334px;
                                    height: 57px;
                                    left: 601px;
                                    top: 85px;

                                    font-family: Roboto;
                                    font-style: normal;
                                    font-weight: bold;
                                    font-size: 28px;
                                    line-height: 57px;

                                }
                            </style>
                        </div>
                        <div>
                            <div id="desc">${Description}</div>
                            <div id="logo">
                                <img src="https://cdn4.iconfinder.com/data/icons/social-media-logos-6/512/112-gmail_email_mail-512.png" width="20%" alt="Logo Pic">
                            </div>
                            <style>
                                #logo{
                                    z-index: 5;
                                    position: relative;
                                    top: 110px;
                                    left: -2px;
                                }
                                #desc{
                                    position: absolute;
                                    width: 478px;
                                    height: 26px;
                                    left: 528px;
                                    top: 148px;

                                    font-family: Roboto;
                                    font-style: normal;
                                    font-weight: bold;
                                    font-size: 22px;
                                    line-height: 26px;
                                    /* identical to box height */


                                    color: #000000;
                                }
                            </style>
                            <br> <div id="name">${Name}</div>
                            <style>
                                #name{
                                    position: absolute;
                                    width: 141px;
                                    height: 26px;
                                    left: 531px;
                                    top: 344px;

                                    font-family: Roboto;
                                    font-style: normal;
                                    font-weight: bold;
                                    font-size: 22px;
                                    line-height: 26px;
                                    /* identical to box height */


                                    color: #000000;

                                }
                            </style><div id="location">${Location}<div>
                                    <style>
                                        #location{
                                            position: absolute;
                                            width: 159px;
                                            height: 26px;
                                            left: 851px;
                                            top: 345px;

                                            font-family: Roboto;
                                            font-style: italic;
                                            font-weight: 500;
                                            font-size: 22px;
                                            line-height: 26px;
                                            /* identical to box height */

                                            color: #000000;
                                        }
                                    </style>
                                    <br>
                                </div>
                    </td>
                </tr>
            </table> <br> <br></td>
    </tr>
</table>
</body>
</html>