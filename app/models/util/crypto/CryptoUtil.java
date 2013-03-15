package models.util.crypto;

import models.exception.JCertifException;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * Hashes and verifies salted SHA-1 passwords, which are compliant with RFC
 * 2307.
 */
public final class CryptoUtil
{
    public static final int PASSWORD_LENGTH = 8;

    private static final Random RANDOM = new SecureRandom();

    private static final int DEFAULT_SALT_SIZE = 8;

    private static int saltLength = Integer.valueOf("20").intValue();

    /**
     * Private constructor to prevent direct instantiation.
     */
    private CryptoUtil()
    {
    }

    public static String generateRandomPassword(){

        String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@$";

        String pw = "";
        int index;

        for (int i=0; i<PASSWORD_LENGTH; i++)
        {
            index = (int)(RANDOM.nextDouble()*letters.length());
            pw += letters.substring(index, index+1);
        }

        return pw;
    }

    /**
     * <p>
     * Creates an RFC 2307-compliant salted, hashed password with the SHA1
     * MessageDigest algorithm. After the password is digested, the first 20
     * bytes of the digest will be the actual password hash; the remaining bytes
     * will be a randomly generated salt of length {@link #DEFAULT_SALT_SIZE},
     * for example: <blockquote><code>3cGWem65NCEkF5Ew5AEk45ak8LHUWAwPVXAyyw==</code></blockquote>
     * </p>
     * <p>
     * In layman's terms, the formula is
     * <code>digest( secret + salt ) + salt</code>. The resulting digest is
     * Base64-encoded.
     * </p>
     * <p>
     * Note that successive invocations of this method with the same password
     * will result in different hashes! (This, of course, is exactly the point.)
     * </p>
     *
     * @param password the password to be digested
     * @return the Base64-encoded password hash
     * @throws JCertifException If your JVM is completely b0rked and does not have SHA.
     */
    public static String getSaltedPassword( byte[] password ) throws JCertifException
    {
        byte[] salt = new byte[DEFAULT_SALT_SIZE];
        RANDOM.nextBytes( salt );
        return getSaltedPassword( password, salt );
    }

    /**
     * <p>
     * Helper method that creates an RFC 2307-compliant salted, hashed password with the SHA1
     * MessageDigest algorithm. After the password is digested, the first 20
     * bytes of the digest will be the actual password hash; the remaining bytes
     * will be the salt. Thus, supplying a password <code>testing123</code>
     * and a random salt <code>foo</code> produces the hash:
     * </p>
     * <blockquote><code>{SSHA}yfT8SRT/WoOuNuA6KbJeF10OznZmb28=</code></blockquote>
     * <p>
     * In layman's terms, the formula is
     * <code>digest( secret + salt ) + salt</code>. The resulting digest is Base64-encoded.</p>
     *
     * @param password the password to be digested
     * @param salt the random salt
     * @return the Base64-encoded password hash, prepended by <code>{SSHA}</code>.
     * @throws JCertifException If your JVM is totally b0rked and does not have SHA1.
     */
    protected static String getSaltedPassword( byte[] password, byte[] salt ) throws JCertifException
    {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            throw new JCertifException(CryptoUtil.class, e.getMessage());
        }
        digest.update( password );
        byte[] hash = digest.digest( salt );

        // Create an array with the hash plus the salt
        byte[] all = new byte[hash.length + salt.length];
        for( int i = 0; i < hash.length; i++ )
        {
            all[i] = hash[i];
        }
        for( int i = 0; i < salt.length; i++ )
        {
            all[hash.length + i] = salt[i];
        }
        byte[] base64 = Base64.encodeBase64( all );
        return new String( base64 );
    }



    /**
     *  Compares a password to a given entry and returns true, if it matches.
     *
     *  @param passwordToCompare The password in bytes.
     *  @param hashedPassword The password entry.
     *  @return True, if the password matches.
     *  @throws JCertifException If there is no SHA available.
     *  @throws JCertifException If no UTF-8 encoding is available
     */
    public static boolean verifySaltedPassword( byte[] passwordToCompare, String hashedPassword ) throws JCertifException {
        try{
        byte[] challenge = Base64.decodeBase64(hashedPassword.getBytes("UTF-8"));

        // Extract the password hash and salt
        byte[] passwordHash = extractPasswordHash( challenge );
        byte[] salt = extractSalt( challenge );

        // Re-create the hash using the password and the extracted salt
        MessageDigest digest = MessageDigest.getInstance( "SHA" );
        digest.update( passwordToCompare );
        byte[] hash = digest.digest( salt );

        // See if our extracted hash matches what we just re-created
        return Arrays.equals( passwordHash, hash );

        }catch (UnsupportedEncodingException uee){
          throw new JCertifException(CryptoUtil.class, uee.getMessage());
        }
        catch (NoSuchAlgorithmException nsae){
            throw new JCertifException(CryptoUtil.class, nsae.getMessage());
        }
    }

    /**
     * Helper method that extracts the hashed password fragment from a supplied salted SHA digest
     * by taking all of the characters before position 20.
     *
     * @param digest the salted digest, which is assumed to have been
     *            previously decoded from Base64.
     * @return the password hash
     * @throws IllegalArgumentException if the length of the supplied digest is
     *             less than or equal to 20 bytes
     */
    protected static byte[] extractPasswordHash( byte[] digest ) throws JCertifException
    {
        if( digest.length < saltLength )
        {
            throw new JCertifException(CryptoUtil.class, "Hash was less than " + saltLength +" characters; could not extract password hash!" );
        }

        // Extract the password hash
        byte[] hash = new byte[saltLength];
        for( int i = 0; i < saltLength; i++ )
        {
            hash[i] = digest[i];
        }

        return hash;
    }

    /**
     * Helper method that extracts the salt from supplied salted digest by taking all of the
     * characters at position 20 and higher.
     *
     * @param digest the salted digest, which is assumed to have been previously
     *            decoded from Base64.
     * @return the salt
     * @throws IllegalArgumentException if the length of the supplied digest is
     *             less than or equal to 20 bytes
     */
    protected static byte[] extractSalt( byte[] digest ) throws JCertifException
    {
        if( digest.length <= saltLength )
        {
            throw new  JCertifException(CryptoUtil.class, "Hash was less than " + saltLength +"+ 1 characters; we found no salt!" );
        }

        // Extract the salt
        byte[] salt = new byte[digest.length - saltLength];
        for( int i = saltLength; i < digest.length; i++ )
        {
            salt[i - saltLength] = digest[i];
        }

        return salt;
    }


}