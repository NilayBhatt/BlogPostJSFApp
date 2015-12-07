package models;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.lang.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.persistence.ManyToOne;

@ManagedBean
@Entity
public class Post implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Author author;
    
    private String title;
    
    private String body;
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setAuthor(Author author) {
        this.author = author;
    }
    
    public Author getAuthor() {
        return this.author;
    }
    
    public String getBody() {
        return this.body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     *
     * @param context
     * @param inputComponent
     * @param value
     * @return
     */
    public boolean validateBody() {
        boolean badWord = false;
        ArrayList<String> words = new ArrayList();
        String tempBody = this.body;
        
        words.add("poop");
        words.add("cat");
        words.add("poodle");

        
        tempBody = tempBody.replaceAll("\\s+","").toLowerCase();
        for(int i = 0; i < words.size(); i++){
        //for( Iterator i = words.iterator(); i.hasNext();) {
            badWord = tempBody.contains(words.get(i));
            if(badWord) break;
        }
        
        return badWord;
    }
}
