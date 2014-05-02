package es.udc.pfcei.web.components;

import java.util.Calendar;		
import java.util.Locale;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
* Java code based on maven arquetype from Integración de Sistemas - Java Facultad de Informática de A Coruña UDC, Spain.
* 
* @link http://www.tic.udc.es/is-java/maven-repo/
*/
public class Layout {
  @Property
  @Parameter(required = false, defaultPrefix = "message")
  private String menuExplanation;

  @Property
  @Parameter(required = true, defaultPrefix = "message")
  private String pageTitle;

  @Inject
  private JavaScriptSupport javaScriptSupport;

  @Inject
  private Locale locale;
  
  @Inject
  private Messages messages;  

  /**
   * Get language for html5
   * @return
   */
  public String getLang() {
    return locale.getLanguage();
  }

  /**
   * Get CSS Bootstrap from external CDN
   * @return
   */
  public String getExternalBootstrapCss() {
    return "//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css";
  }

  /**
   * Get uri for AngularJS ng-app
   * @return
   */
  public String getMyApp() {
    return "MyApp";
  }

  /**
   * Get CSS Theme from Bootstrap from external CDN
   * @return
   */
  public String getExternalBootstrapTheme() {
    return "//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css";
  }
  
  /**
   * Get the text from footer-main with year
   * @return
   */
  public String getFooterMain(){
    return messages.format("footer-main", Calendar.getInstance().get(Calendar.YEAR));
  }

/**
* TODO
* Create a JavaScriptModuleConfiguration named angular
* 
*/
//  public void setupRender(){
//	JavaScriptSupport.require("angular", "angular.module('"+getMyApp()+"',['ui.bootstrap']);");
//  }
}
