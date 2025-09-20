package vn.iotstar.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable; 
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*; 
import lombok.*; 
 
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Table(name = "Categories")
@Entity
@Setter
@Getter
public class Category {
	private static final long serialVersionUID = 1L; 
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId; 
    private String categoryName; 
    private String icon; 
     
   @JsonIgnore 
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL ) 
    private Set<Product> products;
}
