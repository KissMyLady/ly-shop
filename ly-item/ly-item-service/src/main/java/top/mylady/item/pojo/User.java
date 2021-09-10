package top.mylady.item.pojo;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;


@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String phone;
    private Date created;
    private String salt;

}
