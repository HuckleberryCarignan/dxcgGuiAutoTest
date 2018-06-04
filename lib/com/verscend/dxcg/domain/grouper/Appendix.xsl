<?xml version="1.0" encoding="UTF-8" ?>
<!--
// (C) DxCG Inc. Boston, MA 2005
//
// All rights are reserved. Reproduction or transmission in
// whole or in part, in any form or by any means, electronic, 
// mechanical or otherwise, is prohibited without the prior 
// written permission of the copyright owner.
//
// $Author: Susan $
// $Date: 04/06/09 $
// $Workfile: Appendix.xsl $
// $Revision: 1 $
//*****************  Version 2  *****************
//User: rtamrakar    Date: 07/23/09   Time: 1:17p
//Updated in $/DxCG/Config/PostProcess
//Moved css content to separate file 'style_app.css'
//Variable 'supportfilefolder' created to get support file location
//
-->
<xsl:stylesheet version="1.0" xmlns:fn="http://www.w3.org/2005/02/xpath-functions" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:dxcg="http://www.dxcg.com/Appendix.xsd">
  <xsl:output method="html" omit-xml-declaration="yes" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" indent="yes"/>
  <xsl:decimal-format name="format" decimal-separator="." grouping-separator="," />  
  <xsl:variable name="supportfilefolder" select="/dxcg:Appendix//dxcg:Folders//dxcg:FolderType//dxcg:Folder//dxcg:Name"/>
  <xsl:template match="/">
    <html xmlns="http://www.w3.org/1999/xhtml">
      <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
                
        <title>DxCG Intelligence</title>
        <xsl:copy-of select="document('./Appendix.xml')"/>
        <xsl:copy-of select="document('./print.xml')"/>
        <script language="javascript">
          function toggleView(clickableBar, tableId){
          var dataTableStyle = document.getElementById(tableId).style;
          
          //get the first DIV child element..could have used children property but its not so much cross compatible with old browsers
          //we have to access this element to change the arrow image
          var arrowHoldingElement = clickableBar.getElementsByTagName("div")[0];
          
          //get the display property of the table
          var dataTableDisplay = dataTableStyle.display;
          var arrowClass;

          if(dataTableDisplay == ''){
          dataTableDisplay = 'none';
          arrowClass = 'show';
          }
          else{
          dataTableDisplay = '';
          arrowClass = 'hide';
          }

          dataTableStyle.display = dataTableDisplay;
          arrowHoldingElement.className = arrowClass;
          }
        </script>
      </head>
      <body>
        <div id="wrapper">
          <div id="header">
            <div class="header-logo">
              <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAG8AAAAtCAYAAACgVTUHAAAACXBIWXMAAAsSAAALEgHS3X78AAAE/0lEQVR42u1b7W0bORB9G+S/CKgAKxVIHXhdQfYqMF2BFagArwswblOB6QqyrsBUBVlVkFUBBqgKeH+GwJgh98NJHPgwDwgMWcvlcN7M8M0ELrz3ELxPfBAXCHkCIU8g5Al5AiFPIOQJhLz/FT6KC94PCmVWAFYAeu90L5n3vqABPNFPKZty5wmEPIGQJ2rzFxTRBsAmqCIAnXe6m7Fe0foSQEfr+7l2PBd3P9mx9Ltu4r6gva132s1UgyXb086xPWGDpfMnbSiwuN8A+E6fPw1tRsb9oI8X3mnLvqsANADOEkuPALbe6XbE+BrAFsAi+uqLd7qZSNqW3pGy4wSg5CSSw2pScIvEmgcA9QS/GADnia/3dPZuZH0N4DLzyFf6fgvgBsCtd7r+QC89MCk6JlUB4BgRZwB8Yw7bs3+g33+j53IH6MiwBZH9ldYfyTFjpKnn4s4C+Jf2O0V2nAC4iLgNZdg123fP9gU5tKNnc5WmY8TtAdwCeKTP5wDshPWXLNBjG64pC1WqbDYA7onZegJ5Ddu8YRs/UJS5KKoaAJ8BXBbK9N7pOpFx67lZFqFlDrwF0Cz9zkUEr6KMaxnRW++0iewqKXDOAgGJDGyJ+AMAzTOMzt7S2WyhzCrhG8sCR/OkYDaEd6x/Eixk9AnAolBGZyJEs4Ma9uLr4DDvtI7rs3e6905XRCwA3JDRHFv2jtnEPRd3NSPuYul3dUwcACz9jju+YecpY+LIdkt30JEcbDI+AYAqLo1EdBl8m6hshhG3iYljNqzoHVm1aSJH5rKuZQSF3x3ibEoYoVkZ2EaRtYgzegZxir3vy9Lv7ERhEKpFPXQf0VnDOc+j8heExUPuTqT1Jno+ZF0IOD0kjCIbkuQFx63JochsxEmqZjq9ida9uNjnKLsoqBYAjku/m2pHyRwzuoai/xCvpYwIynQIIaBUwnfHVMYlbGhZ8L9sFbzTfaHMI91Nmm3ICdtHERYyZpsrtxEUEzBIOOO18765WRuy51QoYyeuWSUIwETy3IAv7Ay7O+67j4nMCMKiJkIVi5Kc6lv/jSaVBMiaCYe5WGTk/VtiTrXps026d9oWyhyJXU0ZV4WylLrUCf+M9XB/COGu20diZKrDjt7p1V8mr3zFsy43Hmsix9QDWXcYuMPeIuv0SEUYu4PO4vv9DdEyjTEaQPTMmtufIo+3DYbVWDNA9OWYEwplNrlG9ZUwTKiYQhlVKBPK/Njl37EBQjPBcfp3M0c2HGcEn2HVokuSR4qvZdOFrBSmMhqyr80RSKMzS41q9YsZp56LO96QV6xSnA1NMyLULPJtinQKCAPgniZAvxu8BWkHbODn1TnBwg92mWA9V4eDCnoqlNkT+R2puoptfJiqrqjxLgPppPZKdgcDwBUbd7VE4BrA90KZB1rXkx0b1m+G+/2KJkvnAHoiyrJzVazy2D+QfdyGzxkb+Mz16sVYMveHJiShz6kB30xoeg0ZkMNjqhml0dgNtSElI89lBsVh/qfjhnxkQBzwYvg+MlBHbnQW+ehiqFejivQUnzGywQyc90S+a7NqM6Hkmil3AhFSUbmqIgVlaSrTjYiHPtGLafqpWJ9jl37XZuzoAZQjdvSJ5rele61k/VzP1riBe8gmbE9J/Nvcc97plgKvmmNDIX/i9X4h/5Mu5AmEPIGQJ+QJhDyBkCcQ8oQ8gZAnEPKEPIGQJ3hT/Af7anDdVvS0VQAAAABJRU5ErkJggg==" height="44"/>
            </div>
            <h1>DxCG Intelligence</h1>
          </div>
          <div class="content">
            <h2>DxCG Intelligence Data Quality Appendix</h2>
            <br/>
            <xsl:variable name="modelBenchmarkPopulation" select="/dxcg:Appendix//dxcg:ProcessingOptions/dxcg:Models//dxcg:ModelPopulation" />
           
            <div class="table-data">
              <table width="100%" border="0" cellpadding="0" cellspacing="2">
                <tbody>
                  <tr>
                    <th class="tab" colspan="2">
                      <div style="" id="maintab1" class="maintabshow" onclick="toggleView(this, 'datashow1');">
                        <div id="tabhide" class="hide" align="left">RUNTIME DETAIL</div>
                      </div>                     
                    </th>
                  </tr>
                </tbody>
              </table>
              <div style="" id="datashow1">
                <table width="100%" border="0" cellpadding="0" cellspacing="2">
                  <tbody>
                    <tr>
                      <td class="txtdata">Date</td>
                      <td class="txtdata" width="60%">
                        <xsl:value-of select="/dxcg:Appendix//dxcg:Header//dxcg:Date" />
                      </td>
                    </tr>
                    <tr>
                      <td class="txtdata">Completion Time</td>
                      <td class="txtdata">
                        <xsl:value-of select="/dxcg:Appendix//dxcg:Header//dxcg:CompletionTime" />
                      </td>
                    </tr>
                    <tr>
                      <td class="txtdata">Elapsed Time</td>
                      <td class="txtdata">
                        <xsl:value-of select="/dxcg:Appendix//dxcg:Header//dxcg:ElapsedTime" />
                      </td>
                    </tr>
                    <tr>
                      <td class="txtdata">Licensed to</td>
                      <td class="txtdata">
                        <xsl:value-of select="/dxcg:Appendix//dxcg:Header//dxcg:LicensedTo" />
                      </td>
                    </tr>                                        
                    <tr>
                      <td class="txtdata">DxCG Intelligence Release</td>
                      <td class="txtdata">
                        <xsl:value-of select="/dxcg:Appendix//dxcg:Header//dxcg:Release" />
                      </td>
                    </tr>

                    <tr>
                      <td class="txtdata">Code Update Version</td>
                      <td class="txtdata">
                        <xsl:value-of select="/dxcg:Appendix//dxcg:Header//dxcg:CUUVersion" />
                      </td>
                    </tr>
                    
                    <tr>
                      <td class="txtdata">Operating Environment</td>
                      <td class="txtdata">
                        <xsl:value-of select="/dxcg:Appendix//dxcg:Header//dxcg:Environment" />
                      </td>
                    </tr>
                    <tr>
                      <td class="txtdata">Licensed Expiration Date</td>
                      <td class="txtdata">
                        <xsl:value-of select="/dxcg:Appendix//dxcg:Header//dxcg:Expiration" />
                      </td>
                    </tr>
                    <tr>
                      <td class="txtdata">Maximum Licensed Population Size</td>
                      <td class="txtdata">
                        <xsl:value-of select="format-number(/dxcg:Appendix//dxcg:Header//dxcg:MaximumPop, '###,###,##0.')" />
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>              
            </div>
            <br/>

            <div class="table-data">
              <table width="100%" border="0" cellpadding="0" cellspacing="2">
                <tbody>
                  <tr>
                    <th class="tab" colspan="2">
                      <div style="" id="maintab3" class="maintabshow" onclick="toggleView(this, 'datashow2');">
                        <div id="tabhide" class="hide" align="left">FILE LOCATIONS</div>
                      </div>                      
                    </th>
                  </tr>
                </tbody>
              </table>
              <div style="" id="datashow2">
                <table width="100%" border="0" cellpadding="0" cellspacing="2">
                  <tbody>
                    <xsl:for-each select="/dxcg:Appendix//dxcg:Files//dxcg:FileType">
                      <xsl:variable name="Type" select="@Type"/>
                      <xsl:choose>
                        <xsl:when test="string-length(normalize-space($Type))>0">
                          <tr>
                            <td class="txtdata subth">
                              <xsl:value-of select="@Type"/>
                            </td>
                            <td class="txtdata subth" width="60%">&#160;</td>
                          </tr>
                        </xsl:when>
                      </xsl:choose>

                      <xsl:for-each select="dxcg:File">
                        <xsl:variable name="FileName" select="dxcg:Name"/>
                        <xsl:variable name="FileNameLength" select="string-length(dxcg:Location)"/>
                        <xsl:choose>
                          <xsl:when test="string-length(normalize-space($FileName))>0">
                            <tr>
                              <td class="txtdata subdata">
                                <xsl:value-of select="dxcg:Name"/>
                                &#160;
                              </td>
                              <td class="txtdata">
                                
                                <!-- if this is the appendix file, change the extension to html-->
                                <xsl:choose>
                                  <xsl:when test="$FileName = 'Appendix File'">
                                    <xsl:value-of select="substring(dxcg:Location, 1, $FileNameLength - 4)"/>.html
                                  </xsl:when>
                                  <xsl:otherwise>
                                    <xsl:value-of select="dxcg:Location"/>
                                  </xsl:otherwise>
                                </xsl:choose>
                                &#160;
                              </td>
                            </tr>
                          </xsl:when>
                        </xsl:choose>

                      </xsl:for-each>
                    </xsl:for-each>

                  </tbody>
                </table>
              </div>              
            </div>


            <br/>
            <div class="table-data">
              <table width="100%" border="0" cellpadding="0" cellspacing="2">
                <tbody>
                  <tr>
                    <th class="tab" colspan="2">
                      <div style="" id="maintab5" class="maintabshow" onclick="toggleView(this, 'datashow3');">
                        <div id="tabhide" class="hide" align="left">PROCESSING OPTIONS</div>
                      </div>                      
                    </th>
                  </tr>
                </tbody>
              </table>
              <div style="" id="datashow3">
                <table width="100%" border="0" cellpadding="0" cellspacing="2">
                  <tbody>
                    <tr>
                      <td class="txtdata">Client Name</td>
                      <td class="txtdata">
                        <xsl:value-of select="/dxcg:Appendix//dxcg:ProcessingOptions//dxcg:ClientName" />
                      </td>
                    </tr>                    
                    <xsl:variable name="numberOfModels" select="count(/dxcg:Appendix//dxcg:ProcessingOptions/dxcg:Models//dxcg:Model)" />
                    <xsl:for-each select="/dxcg:Appendix//dxcg:ProcessingOptions//dxcg:Models//dxcg:Model">
                      <xsl:variable name="counter" select="position()"/>
                      <tr>
                        <xsl:choose>
                          <xsl:when test="$counter = 1">
                            <td class="txtdata" rowspan="{$numberOfModels}">Model(s) Processed</td>
                            <td class="txtdata" width="60%">
                              Model <xsl:value-of select="@ID"/>, <xsl:value-of select="@Name"/>
                            </td>
                          </xsl:when>
                          <xsl:otherwise>
                            <td class="txtdata" width="60%">
                              Model <xsl:value-of select="@ID"/>, <xsl:value-of select="@Name"/>
                            </td>
                          </xsl:otherwise>
                        </xsl:choose>
                      </tr>
                    </xsl:for-each>
                    <xsl:for-each select="/dxcg:Appendix//dxcg:ProcessingOptions//dxcg:Option">
                      <xsl:variable name="Optionname" select="@Name"/>
                      <xsl:choose>
                        <xsl:when test="string-length(normalize-space($Optionname))>0">
                          <tr>
                            <td class="txtdata">
                              <xsl:value-of select="@Name"/>
                            </td>
                            <td class="txtdata">
                              <xsl:value-of select="."/>
                            </td>
                          </tr>
                        </xsl:when>
                      </xsl:choose>
                    </xsl:for-each>
                    <xsl:for-each select="/dxcg:Appendix//dxcg:ProcessingOptions//dxcg:CustomOutput//dxcg:CustomOption">
                      <tr>
                        <td class="txtdata">
                          <xsl:value-of select="@Name"/>
                        </td>
                        <td class="txtdata">
                          <xsl:value-of select="."/>
                        </td>
                      </tr>
                    </xsl:for-each>
                  </tbody>
                </table>
              </div>              
            </div>

            <br/>
            <div class="table-data">
              <table width="100%" border="0" cellpadding="0" cellspacing="2">
                <tbody>
                  <tr>
                    <th class="tab" colspan="4">
                      <div id="maintab7" class="maintabshow" onclick="toggleView(this, 'datashow4');">
                        <div id="tabhide" class="hide" align="left">DATE COMPARISON</div>
                      </div>                      
                    </th>
                  </tr>
                </tbody>
              </table>
              <div id="datashow4">
                <table width="100%" border="0" cellpadding="0" cellspacing="2">
                  <tbody>
                    <tr>
                      <td colspan="4" class="txtdata notes">
                        If specified, the DIAG_TODATE and RX_SVCDATE should fall in the 12 months prior to the end of the model base period. <br/>
                        *Please refer to the Date Comparison section of the Data Quality Appendix chapter of the User Guide to see how records outside the model base period are handled by DxCG Intelligence.<br/>
                      </td>
                    </tr>
                    <tr>
                      <th width="40%">&#160;</th>
                      <th width="20%">Detail</th>
                      <th width="20%">Count</th>
                      <th width="20%">Percent</th>
                    </tr>
                    <tr>
                      <td class="txtdata subth">Model Base Period</td>
                      <td class="txtdata subth">
                        <xsl:value-of select="/dxcg:Appendix//dxcg:DateComparison//attribute::ModelBasePeriod" />
                      </td>
                      <td class="txtdata subth">&#160;</td>
                      <td class="txtdata subth">&#160;</td>
                    </tr>
                    <xsl:for-each select="/dxcg:Appendix//dxcg:DateComparison//dxcg:Option">
                      <xsl:variable name="OptionLabel" select="@Label"/>
                      <xsl:choose>
                        <xsl:when test="string-length(normalize-space($OptionLabel))>0">
                          <tr>
                            <td class="txtdata subdata">
                              <xsl:value-of select="@Label"/>&#160;
                            </td>
                            <td class="txtdata">
                              <xsl:value-of select="@Detail"/>&#160;
                            </td>
                            <td class="numdata">
                              <xsl:choose>
                                <xsl:when test="format-number(@Count, '#') = 'NaN'">
                                  <xsl:value-of select="@Count"/>&#160;
                                </xsl:when>
                                <xsl:otherwise>
                                  <xsl:value-of select="format-number(@Count, '###,##0')"/>&#160;
                                </xsl:otherwise>
                              </xsl:choose>
                            </td>
                            <td class="numdata">
                              
                              <xsl:choose>
                                <xsl:when test="string-length(@Percent) != 0 ">
                                  <xsl:value-of select="format-number(@Percent, '##0.0')"/>%
                                </xsl:when>
                              </xsl:choose>
                              &#160;
                            </td>
                          </tr>
                        </xsl:when>
                      </xsl:choose>
                    </xsl:for-each>
                  </tbody>
                </table>
              </div>              
            </div>


            <br/>
            <div class="table-data">
              <table width="100%" border="0" cellpadding="0" cellspacing="2">
                <tbody>
                  <tr>
                    <th class="tab" colspan="3">
                      <div id="maintab9" class="maintabshow" onclick="toggleView(this, 'datashow5');">
                        <div id="tabhide" class="hide" align="left">FILE COMPARISON</div>
                      </div>                      
                    </th>
                  </tr>
                </tbody>
              </table>
              <div id="datashow5">
                <table width="100%" border="0" cellpadding="0" cellspacing="2">
                  <tbody>
                    <tr>
                      <xsl:choose>
                        <xsl:when test="$modelBenchmarkPopulation='Commercial'">
                          <th width="40%">&#160;</th>
                          <th width="20%">Count</th>
                          <th width="20%">Percent</th>
                          <th width="20%">Benchmark</th>
                        </xsl:when>
                        <xsl:otherwise>
                          <th width="60%">&#160;</th>
                          <th width="20%">Count</th>
                          <th width="20%">Percent</th>
                        </xsl:otherwise>
                      </xsl:choose>
                    </tr>
                    <xsl:for-each select="/dxcg:Appendix//dxcg:FileComparison//dxcg:File">
                      <xsl:variable name="FileName" select="@Name"/>
                      <xsl:choose>
                        <xsl:when test="string-length(normalize-space($FileName))>0">
                          <tr>
                            <td class="txtdata subth">
                              <xsl:value-of select="@Name"/>
                            </td>
                            <td class="txtdata subth">&#160;</td>
                            <td class="txtdata subth">&#160;</td>
                            <xsl:if test="$modelBenchmarkPopulation='Commercial'">
                              <td class="txtdata subth">&#160;</td>
                            </xsl:if>
                          </tr>
                        </xsl:when>
                      </xsl:choose>

                      <xsl:for-each select="dxcg:Information">
                        <xsl:variable name="InformationLabel" select="@Label"/>
                        <xsl:choose>
                          <xsl:when test="string-length(normalize-space($InformationLabel))>0">
                            <tr>
                              <td class="txtdata subdata">
                                <xsl:value-of select="@Label"/>
                              </td>
                              <td class="numdata">
                                <!-- some of these fields may not be numbers -->
                                <xsl:choose>
                                  <xsl:when test="format-number(@Count, '#') = 'NaN'">
                                    <xsl:value-of select="@Count"/>
                                  </xsl:when>
                                  <xsl:otherwise>
                                    <xsl:value-of select="format-number(@Count, '###,###,##0')"/>
                                  </xsl:otherwise>
                                </xsl:choose>
                              </td>
                              <td class="numdata">
                                <!-- some of these fields may not be numbers -->
                                <xsl:choose>
                                  <xsl:when test="format-number(@Percent, '#') = 'NaN'">
                                    <xsl:value-of select="@Percent"/>
                                  </xsl:when>
                                  <xsl:otherwise>
                                    <xsl:value-of select="format-number(@Percent, '##0.0')"/>%
                                  </xsl:otherwise>
                                </xsl:choose>
                              </td>
                              <xsl:if test="$modelBenchmarkPopulation='Commercial'">
                                <td>
                                  <xsl:choose>
                                    <xsl:when test="format-number(@BenchmarkValue, '#') = 'NaN'">
                                      <xsl:value-of select="@BenchmarkValue"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                      <xsl:choose>
                                        <xsl:when test="@BenchmarkType='Percent'">
                                          <xsl:value-of select="format-number(@BenchmarkValue, '##0.0')"/>%
                                        </xsl:when>
                                        <xsl:when test="@BenchmarkType='Average'">
                                          <xsl:value-of select="format-number(@BenchmarkValue, '##0.0')"/>
                                        </xsl:when>
                                        <xsl:when test="@BenchmarkType='Range'">
                                          <xsl:value-of select="@BenchmarkValue"/>
                                        </xsl:when>
                                      </xsl:choose>
                                    </xsl:otherwise>
                                  </xsl:choose>
                                </td>
                              </xsl:if>
                            </tr>
                          </xsl:when>
                        </xsl:choose>
                      </xsl:for-each>
                    </xsl:for-each>

                  </tbody>
                </table>
              </div>              
            </div>


            <xsl:for-each select="/dxcg:Appendix//dxcg:FileStatistics//dxcg:FileStatistic">
              <xsl:variable name="MemberFileTrue" select="@Name = 'Member File Statistics'"/>
              <xsl:variable name="FileStatisticCategory" select="@Name"/>

              <xsl:variable name="ColspanCount">
                <xsl:choose>
                  <xsl:when test="$MemberFileTrue">4</xsl:when>
                  <xsl:otherwise>5</xsl:otherwise>
                </xsl:choose>
              </xsl:variable>
              <br/>
              <div class="table-data">
                <table width="100%" border="0" cellpadding="0" cellspacing="2">
                  <tbody>
                    <tr>
                      <th class="tab" colspan="{$ColspanCount}">
                        <div id="maintabA{$FileStatisticCategory}" class="maintabshow" onclick="toggleView(this,'datashow{$FileStatisticCategory}');">
                          <div id="tabhide" class="hide" align="left">
                            <xsl:value-of select="@Name"/>
                          </div>
                        </div>                       
                      </th>
                    </tr>
                  </tbody>
                </table>
                <div id="datashow{$FileStatisticCategory}">
                  <table width="100%" border="0" cellpadding="0" cellspacing="2">
                    <tbody>
                      <xsl:choose>
                        <xsl:when test="$MemberFileTrue">
                          <tr>
                            <xsl:choose>
                              <xsl:when test="$modelBenchmarkPopulation='Commercial'">
                                <th width="40%">&#160;</th>
                                <th width="20%">Count</th>
                                <th width="20%">Percent</th>
                                <th width="20%">Benchmark</th>
                              </xsl:when>
                              <xsl:otherwise>
                                <th width="60%">&#160;</th>
                                <th width="20%">Count</th>
                                <th width="20%">Percent</th>
                              </xsl:otherwise>
                            </xsl:choose>
                          </tr>
                        </xsl:when>
                        <xsl:otherwise>
                          <tr>
                            <xsl:choose>
                              <xsl:when test="$modelBenchmarkPopulation='Commercial'">
                                <th width="40%">&#160;</th>
                                <th width="15%">Detail</th>
                                <th width="15%">Count</th>
                                <th width="15%">Percent</th>
                                <th width="15%">Benchmark</th>
                              </xsl:when>
                              <xsl:otherwise>
                                <th width="55%">&#160;</th>
                                <th width="15%">Detail</th>
                                <th width="15%">Count</th>
                                <th width="15%">Percent</th>
                              </xsl:otherwise>
                            </xsl:choose>
                          </tr>
                        </xsl:otherwise>
                      </xsl:choose>

                      <xsl:for-each select="dxcg:DetailHeader">
                        <xsl:variable name="HeaderName" select="@Name"/>
                        <xsl:choose>
                          <xsl:when test="string-length(normalize-space($HeaderName))>0">
                            <tr>
                              <td class="txtdata subth">
                                <xsl:value-of select="@Name"/>
                              </td>
                              <td class="txtdata subth">&#160;</td>
                              <td class="txtdata subth">&#160;</td>
                              <xsl:if test="$modelBenchmarkPopulation='Commercial'">
                                <td class="txtdata subth">&#160;</td>
                              </xsl:if>
                              <xsl:choose>
                                <xsl:when test="$MemberFileTrue = 0">
                                  <td class="txtdata subth">&#160;</td>
                                </xsl:when>
                              </xsl:choose>
                            </tr>
                          </xsl:when>
                        </xsl:choose>
                        <xsl:variable name="Subtitle" select="@Subtitle"/>
                        <xsl:choose>
                          <xsl:when test="string-length(normalize-space($Subtitle))>0">
                            <tr>
                              <td class="txtdata">
                                <xsl:value-of select="@Subtitle"/>
                              </td>
                              <td class="txtdata">&#160;</td>
                              <td class="txtdata">&#160;</td>
                              <td class="txtdata">&#160;</td>
                              <xsl:if test="$modelBenchmarkPopulation='Commercial'">
                                <td class="txtdata">&#160;</td>
                              </xsl:if>
                            </tr>
                          </xsl:when>
                        </xsl:choose>
                        <xsl:for-each select="dxcg:Information">
                          <xsl:variable name="LabelName" select="@Label"/>
                          <xsl:choose>
                            <xsl:when test="string-length(normalize-space($LabelName))>0">
                              <tr>
                                <td class="txtdata subdata">
                                  <xsl:value-of select="@Label"/>
                                </td>
                                <xsl:choose>
                                  <xsl:when test ="$MemberFileTrue = 0">
                                    <td class="numdata">
                                      <xsl:choose>
                                        <xsl:when test="format-number(@Detail, '#') = 'NaN' ">
                                          <xsl:value-of select="@Detail"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                          <xsl:value-of select="format-number(@Detail,'###,##0.0') "/>
                                        </xsl:otherwise>
                                      </xsl:choose>
                                      &#160;
                                    </td>
                                  </xsl:when>
                                </xsl:choose>
                                <td class="numdata">
                                  <!-- some of these fields may not be numbers -->
                                  <xsl:choose>
                                    <xsl:when test="format-number(@Count, '#') = 'NaN'">
                                      <xsl:value-of select="@Count"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                      <xsl:value-of select="format-number(@Count, '###,###,##0')"/>
                                    </xsl:otherwise>
                                  </xsl:choose>
                                  &#160;
                                </td>
                                <td class="numdata">
                                  <!-- some of these fields may not be numbers -->
                                  <xsl:choose>
                                    <xsl:when test="format-number(@Percent, '#') = 'NaN'">
                                      <xsl:value-of select="@Percent"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                      <xsl:value-of select="format-number(@Percent, '##0.0')"/>%
                                    </xsl:otherwise>
                                  </xsl:choose>
                                  &#160;
                                </td>
                                <xsl:if test="$modelBenchmarkPopulation='Commercial'">
                                  <td>
                                    <xsl:choose>
                                      <xsl:when test="format-number(@BenchmarkValue, '#') = 'NaN'">
                                        <xsl:value-of select="@BenchmarkValue"/>
                                      </xsl:when>
                                      <xsl:otherwise>
                                        <xsl:choose>
                                          <xsl:when test="@BenchmarkType='Percent'">
                                            <xsl:value-of select="format-number(@BenchmarkValue, '##0.0')"/>%
                                          </xsl:when>
                                          <xsl:when test="@BenchmarkType='Average'">
                                            <xsl:value-of select="format-number(@BenchmarkValue, '##0.0')"/>
                                          </xsl:when>
                                          <xsl:when test="@BenchmarkType='Range'">
                                            <xsl:value-of select="@BenchmarkValue"/>
                                          </xsl:when>
                                        </xsl:choose>
                                      </xsl:otherwise>
                                    </xsl:choose>
                                  </td>
                                </xsl:if>
                              </tr>
                            </xsl:when>
                          </xsl:choose>
                        </xsl:for-each>
                      </xsl:for-each>

                    </tbody>
                  </table>
                </div>                
              </div>
            </xsl:for-each>

            <br/>
            <div class="table-data">
              <table width="100%" border="0" cellpadding="0" cellspacing="2">
                <tbody>
                  <tr>
                    <th class="tab" colspan="3">
                      <div id="maintab11" class="maintabshow" onclick="toggleView(this, 'datashow6');">
                        <div id="tabhide" class="hide" align="left">DATA CLEANSING</div>
                      </div>                     
                    </th>
                  </tr>
                </tbody>
              </table>
              <div id="datashow6">
                <table width="100%" border="0" cellpadding="0" cellspacing="2">
                  <tbody>
                    <tr>
                      <xsl:choose>
                        <xsl:when test="$modelBenchmarkPopulation='Commercial'">
                          <th width="55%">&#160;</th>
                          <th width="15%">Original Input - Count</th>
                          <th width="15%">Reconciled Input - Count</th>
                          <th width="15%">Benchmark</th>
                        </xsl:when>
                        <xsl:otherwise>
                          <th width="60%">&#160;</th>
                          <th width="20%">Original Input - Count</th>
                          <th width="20%">Reconciled Input - Count</th>
                        </xsl:otherwise>
                      </xsl:choose>
                    </tr>
                    <xsl:for-each select="/dxcg:Appendix//dxcg:DataCleansing//dxcg:DetailHeader">
                      <xsl:variable name="DetailHeaderName" select="@Name"/>
                      <xsl:choose>
                        <xsl:when test="string-length(normalize-space($DetailHeaderName))>0">
                          <tr>
                            <td class="txtdata subth">
                              <xsl:value-of select="@Name"/>
                            </td>
                            <td class="txtdata subth">&#160;</td>
                            <td class="txtdata subth">&#160;</td>
                            <xsl:if test="$modelBenchmarkPopulation='Commercial'">
                              <td class="txtdata subth">&#160;</td>
                            </xsl:if>
                          </tr>
                        </xsl:when>
                      </xsl:choose>
                      <xsl:for-each select="dxcg:Information">
                        <xsl:variable name="InformationLabel" select="@Label"/>
                        <xsl:choose>
                          <xsl:when test="string-length(normalize-space($InformationLabel))>0">
                            <tr>
                              <td class="txtdata subdata">
                                <xsl:value-of select="@Label"/>
                              </td>
                              <td class="numdata">
                                <xsl:choose>
                                  <xsl:when test="format-number(@Count, '#') = 'NaN'">
                                    <xsl:value-of select="@Count"/>
                                  </xsl:when>
                                  <xsl:otherwise>
                                    <xsl:value-of select="format-number(@Count, '###,##0.##')"/>
                                  </xsl:otherwise>
                                </xsl:choose>
                              </td>
                              <td class="numdata">
                                <xsl:choose>
                                  <xsl:when test="format-number(@Percent, '#') = 'NaN'">
                                    <xsl:value-of select="@Percent"/>
                                  </xsl:when>
                                  <xsl:otherwise>
                                    <xsl:value-of select="format-number(@Percent, '###,##0.##')"/>
                                  </xsl:otherwise>
                                </xsl:choose>
                              </td>
                              <xsl:if test="$modelBenchmarkPopulation='Commercial'">
                                <td>
                                  <xsl:choose>
                                    <xsl:when test="format-number(@BenchmarkValue, '#') = 'NaN'">
                                      <xsl:value-of select="@BenchmarkValue"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                      <xsl:choose>
                                        <xsl:when test="@BenchmarkType='Percent'">
                                          <xsl:value-of select="format-number(@BenchmarkValue, '##0.0')"/>%
                                        </xsl:when>
                                        <xsl:when test="@BenchmarkType='Average'">
                                          <xsl:value-of select="format-number(@BenchmarkValue, '##0.0')"/>
                                        </xsl:when>
                                        <xsl:when test="@BenchmarkType='Range'">
                                          <xsl:value-of select="@BenchmarkValue"/>
                                        </xsl:when>
                                      </xsl:choose>
                                    </xsl:otherwise>
                                  </xsl:choose>
                                </td>
                              </xsl:if>
                            </tr>
                          </xsl:when>
                        </xsl:choose>
                      </xsl:for-each>
                    </xsl:for-each>
                  </tbody>
                </table>
              </div>              
            </div>

            <br/>
            <div class="table-data">
              <table width="100%" border="0" cellpadding="0" cellspacing="2">
                <tbody>
                  <tr>
                    <th class="tab" colspan="4">
                      <div id="maintab13" class="maintabshow" onclick="toggleView(this, 'datashow7');">
                        <div id="tabhide" class="hide" align="left">OUTPUT FILE STATISTICS</div>
                      </div>                      
                    </th>
                  </tr>
                </tbody>
              </table>
              <div id="datashow7">
                <table width="100%" border="0" cellpadding="0" cellspacing="2">
                  <tbody>
                    <tr>
                      <xsl:choose>
                        <xsl:when test="$modelBenchmarkPopulation='Commercial'">
                          <th width="40%">&#160;</th>
                          <th width="15%">Detail</th>
                          <th width="15%">Count</th>
                          <th width="15%">Percent</th>
                          <th width="15%">Benchmark</th>
                        </xsl:when>
                        <xsl:otherwise>
                          <th width="55%">&#160;</th>
                          <th width="15%">Detail</th>
                          <th width="15%">Count</th>
                          <th width="15%">Percent</th>
                        </xsl:otherwise>
                      </xsl:choose>
                    </tr>
                  
                     <xsl:for-each select="/dxcg:Appendix//dxcg:OutputFileStatistics//dxcg:FileStatistic//dxcg:DetailHeader">
                      <xsl:variable name="DetailHeader" select="@Name"/>
                      <xsl:choose>
                        <xsl:when test="string-length(normalize-space($DetailHeader))>0">
                          <tr>
                            <td class="txtdata subth">
                              <xsl:value-of select="@Name"/>
                            </td>
                            <td class="txtdata subth">&#160;</td>
                            <td class="txtdata subth">&#160;</td>
                            <td class="txtdata subth">&#160;</td>
                            <xsl:if test="$modelBenchmarkPopulation='Commercial'">
                              <td class="txtdata subth">&#160;</td>
                            </xsl:if>
                          </tr>
                        </xsl:when>
                      </xsl:choose>

                      <xsl:variable name="Subtitle" select="@Subtitle"/>
                      <xsl:choose>
                        <xsl:when test="string-length(normalize-space($Subtitle))>0">
                          <tr>
                            <td class="txtdata">
                              <xsl:value-of select="@Subtitle"/>
                            </td>
                            <td class="txtdata">&#160;</td>
                            <td class="txtdata">&#160;</td>
                            <xsl:if test="$modelBenchmarkPopulation='Commercial'">
                              <td class="txtdata subth">&#160;</td>
                            </xsl:if>
                          </tr>
                        </xsl:when>
                      </xsl:choose>
                      <xsl:for-each select="dxcg:Information">
                        <xsl:variable name="Label" select="@Label"/>
                        <xsl:choose>
                          <xsl:when test="string-length(normalize-space($Label))>0">
                            <tr>
                              <td class="txtdata subdata">
                                <xsl:value-of select="@Label"/>
                              </td>
                              
                              <td class="numdata">
                              <xsl:choose>
                                <xsl:when test="@Label != 'Person Years'">
                                  <xsl:choose>
                                    <xsl:when test="format-number(@Detail, '#') = 'NaN'">
                                      <xsl:value-of select="@Detail"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                      <xsl:value-of select="format-number(@Detail, '###,##0.0')"/>
                                    </xsl:otherwise>
                                  </xsl:choose>
                                </xsl:when>
                                <xsl:otherwise>
                                  <xsl:value-of select="format-number(@Detail, '###,##0.0')"/>
                                </xsl:otherwise>
                              </xsl:choose>
                                &#160;
                              </td>
                              <td class="numdata">
                                <xsl:choose>
                                  <xsl:when test="format-number(@Count, '#') = 'NaN'">
                                    <xsl:value-of select="@Count"/>
                                  </xsl:when>
                                  <xsl:otherwise>
                                    <xsl:value-of select="format-number(@Count, '###,##0.##')"/>
                                  </xsl:otherwise>
                                </xsl:choose>
                                &#160;
                              </td>
                              <td class="numdata">
                                <xsl:choose>
                                  <xsl:when test="format-number(@Percent, '#') = 'NaN'">
                                    <xsl:value-of select="@Percent"/>
                                  </xsl:when>
                                  <xsl:otherwise>
                                    <xsl:value-of select="format-number(@Percent, '##0.0')"/>%
                                  </xsl:otherwise>
                                </xsl:choose>
                                &#160;
                              </td>
                              <xsl:if test="$modelBenchmarkPopulation='Commercial'">
                              <td>
                                <xsl:choose>
                                  <xsl:when test="format-number(@BenchmarkValue, '#') = 'NaN'">
                                    <xsl:value-of select="@BenchmarkValue"/>
                                  </xsl:when>
                                  <xsl:otherwise>
                                    <xsl:choose>
                                      <xsl:when test="@BenchmarkType='Percent'">
                                        <xsl:value-of select="format-number(@BenchmarkValue, '##0.0')"/>%
                                      </xsl:when>
                                      <xsl:when test="@BenchmarkType='Average'">
                                        <xsl:value-of select="format-number(@BenchmarkValue, '##0.0')"/>
                                      </xsl:when>
                                      <xsl:when test="@BenchmarkType='Range'">
                                        <xsl:value-of select="@BenchmarkValue"/>
                                      </xsl:when>
                                    </xsl:choose>
                                  </xsl:otherwise>
                                </xsl:choose>
                              </td>
                             </xsl:if>
                            </tr>
                          </xsl:when>
                        </xsl:choose>
                      </xsl:for-each>
                    </xsl:for-each>

                  </tbody>
                </table>
              </div>              
            </div>

            <br/>
            <div class="table-data">
              <table width="100%" border="0" cellpadding="0" cellspacing="2">
                <tbody>
                  <tr>
                    <th class="tab" colspan="4">
                      <div id="maintab15" class="maintabshow" onclick="toggleView(this, 'datashow8');">
                        <div id="tabhide" class="hide" align="left">EXPENDITURE INFORMATION</div>
                      </div>                      
                    </th>
                  </tr>
                </tbody>
              </table>
              <div id="datashow8">
                <table width="100%" border="0" cellpadding="0" cellspacing="2">
                  <tbody>
                    <tr>
                      <xsl:choose>
                        <xsl:when test="$modelBenchmarkPopulation='Commercial'">
                          <th width="40%">&#160;</th>
                          <th width="15%">Medical</th>
                          <th width="15%">Pharmacy</th>
                          <th width="15%">Medical Plus Pharmacy</th>
                          <th width="15%">Benchmark</th>
                        </xsl:when>
                        <xsl:otherwise>
                          <th width="55%">&#160;</th>
                          <th width="15%">Medical</th>
                          <th width="15%">Pharmacy</th>
                          <th width="15%">Medical Plus Pharmacy</th>
                        </xsl:otherwise>
                      </xsl:choose>
                    </tr>
                    <xsl:for-each select="/dxcg:Appendix//dxcg:Expenditures//dxcg:ExpendituresHeader">
                      <xsl:variable name="ExpHeader" select="@Name"/>
                      <xsl:choose>
                        <xsl:when test="string-length(normalize-space($ExpHeader))>0">
                          <tr>
                            <td class="txtdata subth">
                              <xsl:value-of select="@Name"></xsl:value-of>
                            </td>
                            <td class="txtdata subth">&#160;</td>
                            <td class="txtdata subth">&#160;</td>
                            <td class="txtdata subth">&#160;</td>
                            <xsl:if test="$modelBenchmarkPopulation='Commercial'">
                              <td class="txtdata subth">&#160;</td>
                            </xsl:if>
                          </tr>
                        </xsl:when>
                      </xsl:choose>
                      <xsl:for-each select="dxcg:ExpenditureInformation">
                        <xsl:variable name="ExpLabel" select="@Label"/>
                        <xsl:choose>
                          <xsl:when test="string-length(normalize-space($ExpLabel))>0">
                            <tr>
                              <td class="txtdata subdata">
                                <xsl:value-of select="@Label"></xsl:value-of>
                              </td>
                              <td class="numdata">                               
                                <xsl:choose>
                                  <xsl:when test="@Medical ='N/A'">N/A</xsl:when>
                                  <xsl:otherwise>
                                      <xsl:choose>
                                        <xsl:when test="@ShowDollarSign = 'false'"></xsl:when>
                                        <xsl:otherwise> $
                                        </xsl:otherwise>
                                      </xsl:choose>
                                    <xsl:choose>                                      
                                      <xsl:when test="format-number(@Medical, '#') = 'NaN'">
                                        <xsl:value-of select="@Medical"/>
                                      </xsl:when>
                                      <xsl:otherwise>
                                        <xsl:value-of select="format-number(@Medical, '###,##0')"/>
                                      </xsl:otherwise>
                                    </xsl:choose>
                                  </xsl:otherwise>
                                </xsl:choose>                               
                              </td>
                              <td class="numdata">
                                <xsl:choose>
                                  <xsl:when test="@Pharmacy ='N/A'">N/A</xsl:when>
                                  <xsl:otherwise>
                                    <xsl:choose>
                                      <xsl:when test="@ShowDollarSign = 'false'"></xsl:when>
                                      <xsl:otherwise>
                                        $
                                      </xsl:otherwise>
                                    </xsl:choose>
                                    <xsl:choose>
                                      <xsl:when test="format-number(@Pharmacy, '#') = 'NaN'">
                                        <xsl:value-of select="@Pharmacy"/>
                                      </xsl:when>
                                      <xsl:otherwise>
                                        <xsl:value-of select="format-number(@Pharmacy, '#,###,##0.')"/>
                                      </xsl:otherwise>
                                    </xsl:choose>
                                  </xsl:otherwise>
                                </xsl:choose>
                              </td>
                              
                              
                              <!--<td class="numdata">
                                <xsl:choose>
                                  <xsl:when test="@ShowDollarSign = 'false'"></xsl:when>
                                  <xsl:otherwise>$</xsl:otherwise>
                                </xsl:choose>
                                <xsl:value-of select="@Pharmacy"/>
                              </td>-->

                              <td class="numdata">
                                <xsl:choose>
                                  <xsl:when test="@MedicalPharmacy ='N/A'">N/A</xsl:when>
                                  <xsl:otherwise>
                                    <xsl:choose>
                                      <xsl:when test="@ShowDollarSign = 'false'"></xsl:when>
                                      <xsl:otherwise>
                                        $
                                      </xsl:otherwise>
                                    </xsl:choose>
                                    <xsl:choose>
                                      <xsl:when test="format-number(@MedicalPharmacy, '#') = 'NaN'">
                                        <xsl:value-of select="@MedicalPharmacy"/>
                                      </xsl:when>
                                      <xsl:otherwise>
                                        <xsl:value-of select="format-number(@MedicalPharmacy, '#,###,##0')"/>
                                      </xsl:otherwise>
                                    </xsl:choose>                                                                       
                                  </xsl:otherwise>
                                </xsl:choose>
                              </td>
                              <xsl:if test="$modelBenchmarkPopulation='Commercial'">
                                <td>
                                  <xsl:choose>
                                    <xsl:when test="format-number(@BenchmarkValue, '#') = 'NaN'">
                                      <xsl:value-of select="@BenchmarkValue"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                      <xsl:choose>
                                        <xsl:when test="@BenchmarkType='Percent'">
                                          <xsl:value-of select="format-number(@BenchmarkValue, '##0.0')"/>%
                                        </xsl:when>
                                        <xsl:when test="@BenchmarkType='Average'">
                                          <xsl:value-of select="format-number(@BenchmarkValue, '##0.0')"/>
                                        </xsl:when>
                                        <xsl:when test="@BenchmarkType='Range'">
                                          <xsl:value-of select="@BenchmarkValue"/>
                                        </xsl:when>
                                      </xsl:choose>
                                    </xsl:otherwise>
                                  </xsl:choose>
                                </td>
                              </xsl:if>
                             
                              
                              <!--<td class="numdata">
                                <xsl:choose>
                                  <xsl:when test="@ShowDollarSign = 'false'"></xsl:when>
                                  <xsl:otherwise>$</xsl:otherwise>
                                </xsl:choose>
                                <xsl:value-of select="@MedicalPharmacy"/>
                              </td>-->
                            </tr>
                          </xsl:when>
                        </xsl:choose>
                      </xsl:for-each>
                    </xsl:for-each>

                  </tbody>
                </table>
              </div>              
            </div>


            <br/>
            <div class="table-data">
              <table width="100%" border="0" cellpadding="0" cellspacing="2">
                <tbody>
                  <tr>
                    <th class="tab" colspan="3">
                      <div id="maintab17" class="maintabshow" onclick="toggleView(this, 'datashow9');">
                        <div id="tabhide" class="hide" align="left">PREDICTION SUMMARY</div>
                      </div>                      
                    </th>
                  </tr>
                </tbody>
              </table>
              <div id="datashow9">
                <table width="100%" border="0" cellpadding="0" cellspacing="2">
                  <tbody>
                    <xsl:for-each select="/dxcg:Appendix//dxcg:RiskScores//dxcg:RiskScoreHeader">
                      <xsl:variable name="RiskScoreHeader" select="@Label"/>
                      <xsl:choose>
                        <xsl:when test="string-length(normalize-space($RiskScoreHeader))>0">
                          <tr>
                            <th width="60%">
                              <xsl:value-of select="@Label"></xsl:value-of>
                            </th>
                            <th width="20%">&#160;</th>
                            <th width="40%">&#160;</th>
                          </tr>
                        </xsl:when>
                      </xsl:choose>
                      <xsl:for-each select="dxcg:RiskScore">
                        <tr>
                          <xsl:choose>
                            <xsl:when test="@IsAgeGenderModel='true'">
                              <td class="txtdata subdatafade">
                                <xsl:value-of select="@Label"/> (ID: <xsl:value-of select="@ID"/>)
                              </td>
                            </xsl:when>
                            <xsl:otherwise>
                              <td class="txtdata">
                                <xsl:value-of select="@Label"/> (ID: <xsl:value-of select="@ID"/>)
                              
                            </td>
                            </xsl:otherwise>
                          </xsl:choose>
                          <td>&#160;</td>
                          <xsl:choose>
                            <xsl:when test="@IsAgeGenderModel='true'">
                              <td class="txtdatafade">
                                <!-- check to see if it is a number-->
                                <xsl:choose>
                                  <xsl:when test="format-number(@RiskScoreValue, '#') = 'NaN'">
                                    <xsl:value-of select="@RiskScoreValue"/>
                                  </xsl:when>
                                  <xsl:otherwise>
                                    <xsl:value-of select="format-number(@RiskScoreValue, '###,##0.000')"/>
                                  </xsl:otherwise>
                                </xsl:choose>
                              </td>
                            </xsl:when>
                            <xsl:otherwise>
                              <td class="numdata">
                                <!-- check to see if it is a number-->
                                <xsl:choose>
                                  <xsl:when test="format-number(@RiskScoreValue, '#') = 'NaN'">
                                    <xsl:value-of select="@RiskScoreValue"/>
                                  </xsl:when>
                                  <xsl:otherwise>
                                    <xsl:value-of select="format-number(@RiskScoreValue, '###,##0.000')"/>
                                  </xsl:otherwise>
                                </xsl:choose>
                              </td>
                            </xsl:otherwise>
                          </xsl:choose>
                        </tr>
                      </xsl:for-each>
                    </xsl:for-each>

                  </tbody>
                </table>
              </div>              
            </div>


          </div>
        </div>
        <div id="footer">
          <table width="100%" border="0" cellpadding="0" cellspacing="2">
            <tbody>
              <tr>
                <td width="45%">
                  <div class="date">

                  </div>
                </td>
                <td width="45%">
                  <div align="right" class="date">

                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
