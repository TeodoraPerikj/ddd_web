package mk.ukim.finki.emt.commentmanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

@Getter
public class CommentsDto implements ValueObject {
    @JsonProperty("comment")
    private final String comment;

    @JsonProperty("title")
    private final String title;

    private CommentsDto(){
        this.comment = "";
        this.title = "";
    }

    @JsonCreator
    public CommentsDto(String comment, String title){
        this.comment = comment;
        this.title = title;
    }
}
