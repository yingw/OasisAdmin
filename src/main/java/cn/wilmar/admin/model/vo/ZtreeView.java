package cn.wilmar.admin.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * @author Yin Guo Wei 2018/4/8.
 */
@Data
public class ZtreeView implements Serializable {

    private Integer id;

    private Integer pId;

    private String name;

    private boolean open = true;

    private boolean checked = false;

}