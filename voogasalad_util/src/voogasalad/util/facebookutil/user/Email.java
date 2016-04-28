package voogasalad.util.facebookutil.user;

/**
 * Class to help with managing emails. Easier to deal with emails
 * in terms of a name and domain
 * @author Tommy
 *
 */
public class Email {
    private static final String AT_UNICODE = "\\\\u0040";
    private String myName;
    
    private String myDomain;
    
    public Email (String name, String domain) {
        myName = name;
        myDomain = domain;
    }
    
    public Email (String address) {
        if (address != null) {
            parseAddress(address);
        }
    }

    private void parseAddress (String address) {
        String[] split = address.split(AT_UNICODE);
        myName = split[0];
        myDomain = split[1];       
    }
    
    public String getName () {
        return myName;
    }
    
    public String getDomain () {
        return myDomain;
    }
    
    @Override
    public String toString () {
        return myName + "@" + myDomain;
    }
    
    @Override
    public boolean equals (Object obj) {
        if (! (obj instanceof Email)) {
            return false;
        }
        Email other = (Email) obj;
        return (other.getName().equals(getName())) &&
               (other.getDomain().equals(getDomain()));
    }

}
