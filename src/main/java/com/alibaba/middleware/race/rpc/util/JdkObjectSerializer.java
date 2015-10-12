package com.alibaba.middleware.race.rpc.util;

import java.io.*;

public class JdkObjectSerializer {

    public static <T> T deserialize( final byte[] bytes , final Class<T> clazz ) {
        final ByteArrayInputStream in = new ByteArrayInputStream( bytes );
        try {
            final ObjectInputStream ois = new ObjectInputStream( in );
            Object obj = ois.readObject();
            return clazz.cast( obj );
        }
        catch ( final ClassNotFoundException e ) {
            throw new IllegalStateException( e.getMessage() , e );
        }
        catch ( final IOException e ) {
            throw new IllegalStateException( e.getMessage() , e );
        }
        finally {
            try {
                in.close();
            }
            catch ( final IOException e ) {
                throw new IllegalStateException( e.getMessage() , e );
            }
        }
    }

    public static <T> byte[] serialize( final T source ) {

        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            final ObjectOutputStream oos = new ObjectOutputStream( out );
            oos.writeObject( source );
            oos.flush();
        }
        catch ( final IOException e ) {
            throw new IllegalStateException( e.getMessage() , e );
        }
        finally {
            try {
                out.close();
            }
            catch ( final IOException e ) {
                throw new IllegalStateException( e.getMessage() , e );
            }
        }

        return out.toByteArray();
    }
}
