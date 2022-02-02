package YHWLTH.sharing.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult {
    private String result;
    private String code;
    private String msg;
    private HashMap<String, String> validationMsg = new HashMap<>();
}
