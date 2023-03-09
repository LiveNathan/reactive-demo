package cnLabs.reactivedemo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    // There is no @Entity annotation here, as this is not Spring Data JPA.
    // There is also no @GeneratedValue required, as this is the default behavior for Spring Data R2DBC.
    @Id
    private Integer id;
    private String name;
}