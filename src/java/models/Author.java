package models;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.SessionBean;
import javax.faces.bean.ManagedBean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.servlet.http.HttpSession;


@ManagedBean
@Entity
public class Author implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String password;
    
    @OneToMany(mappedBy="author")
    private Collection<Post> posts;

    public Author() {}

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    public Collection<Post> getPosts() {
        return this.posts;
    }
    
    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
       this.password = hash(password);
    }
    
    public void setRawPassword(String password) {
        this.password = password;
    }
    
    private String hash(String password) {
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Author.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] bs;
        msgDigest.reset();
        bs = msgDigest.digest(password.getBytes());
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < bs.length; i++) {
            String hexVal = Integer.toHexString(0xFF & bs[i]);
            if (hexVal.length() == 1) {
                sBuilder.append("0");
            }
            sBuilder.append(hexVal);
        }
        return sBuilder.toString();
    }
    
}