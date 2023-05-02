//package org.acme.getting.started.filter;
//
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.ext.Provider;
//import java.io.IOException;
//import java.util.zip.GZIPInputStream;
//
//@Provider
//public class GzipSupport implements ContainerRequestFilter {
//    @Override
//    public void filter(ContainerRequestContext requestContext) throws IOException {
//        String encoding = requestContext.getHeaderString("content-encoding");
//        if(encoding != null && encoding.equals("gzip")) {
//            System.out.println("gzip content is sent");
//            GZIPInputStream is = new GZIPInputStream( requestContext.getEntityStream() );
//            requestContext.setEntityStream(is);
//        }
//    }
//}
