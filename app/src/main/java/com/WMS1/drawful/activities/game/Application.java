package com.WMS1.drawful.activities.game;

import com.WMS1.drawful.R;
import com.WMS1.drawful.services.RefreshTokenService;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        allowSSL();

        RefreshTokenService service = new RefreshTokenService();
        service.runService(getApplicationContext());
    }

    /* https://gist.github.com/polilluminato/0b89922983cd92c4f99413001a7373ad */
    public void allowSSL(){
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            try(InputStream caInput = this.getResources().openRawResource(R.raw.chain)){
                Certificate ca = cf.generateCertificate(caInput);

                // Create a KeyStore containing our trusted CAs
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(null, null);
                keyStore.setCertificateEntry("ca", ca);

                // Create a TrustManager that trusts the CAs in our KeyStore
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init(keyStore);

                // Create an SSLContext that uses our TrustManager
                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, tmf.getTrustManagers(), null);

                // Set default factory
                HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

            } catch (IOException | KeyStoreException | KeyManagementException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } catch (CertificateException e) {
            e.printStackTrace();
        }
    }

}
