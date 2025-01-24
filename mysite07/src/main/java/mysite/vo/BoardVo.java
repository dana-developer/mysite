package mysite.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardVo {
	private Long id;
	private String title;
	private String contents;
	private Long hit;
	private String regDate;
	private Long gNo;
	private Long oNo;
	private Long depth;
	private Long userId;
	private String userName;
	private String type;
}
