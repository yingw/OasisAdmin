package cn.yinguowei.oasis.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
//import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
//import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
//import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;

import java.io.FileNotFoundException;

/**
 * @author yinguowei 2017/8/6.
 */
//@Configuration
public class TomcatSSLConfig {
    @Value("${keystore.file}")
    private String keystoreFile;
    @Value("${keystore.pass}")
    private String keystorePass;
// https://stackoverflow.com/questions/47832999/embeddedservletcontainercustomizer-and-configurableembeddedservletcontainer-in-s
//    @Bean
//public EmbeddedServletContainerCustomizer containerCustomizer() throws FileNotFoundException {
    public ConfigurableServletWebServerFactory containerCustomizer() throws FileNotFoundException {
        final String absoluteKeystoreFile = TomcatSSLConfig.class.getResource("/").getPath() + keystoreFile;// ResourceUtils.getFile(keystoreFile).getAbsolutePath();

//        return new EmbeddedServletContainerCustomizer() {
        return new TomcatServletWebServerFactory() {
//            @Override ??
//            public void customize(ConfigurableEmbeddedServletContainer factory) {
                public void customize(ConfigurableTomcatWebServerFactory factory) {
                System.out.println("absoluteKeystoreFile = " + absoluteKeystoreFile);
                if (factory instanceof TomcatServletWebServerFactory) {
//                    TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) factory;
                    TomcatServletWebServerFactory webServerFactory = (TomcatServletWebServerFactory) factory;
                    webServerFactory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
                        @Override
                        public void customize(Connector connector) {
                            connector.setPort(8443);
                            connector.setSecure(true);
                            connector.setScheme("https");
                            Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
                            proto.setSSLEnabled(true);
                            proto.setKeystoreFile(absoluteKeystoreFile);
                            proto.setKeystorePass(keystorePass);
                            proto.setKeystoreType("PKCS12");
                            proto.setKeyAlias("tomcat");
                        }
                    });
                }
            }
        };
    }
}
