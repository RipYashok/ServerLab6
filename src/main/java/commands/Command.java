package commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import commands.utils.CommandType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = InsertNull.class, name = "INSERTNULL"),
        @JsonSubTypes.Type(value = Show.class, name = "SHOW"),
        @JsonSubTypes.Type(value = Clear.class, name = "CLEAR"),
        @JsonSubTypes.Type(value = RemoveKey.class, name = "REMOVEKEY"),
        @JsonSubTypes.Type(value = MaxByID.class, name = "MAXID"),
        @JsonSubTypes.Type(value = AverageOfStudentsCount.class, name = "AVERAGEOFSTUDENTSCOUNT"),
        @JsonSubTypes.Type(value = RemoveAllByOfEducation.class, name = "REMOVEALLBYOFEDUCATION"),
        @JsonSubTypes.Type(value = RemoveLower.class, name = "REMOVELOWER"),
        @JsonSubTypes.Type(value = ReplaceIfLowe.class, name = "REPLACELOWE"),
        @JsonSubTypes.Type(value = History.class, name = "HISTORY"),
        @JsonSubTypes.Type(value = Info.class, name = "INFO"),
        // Добавьте здесь другие подклассы commands.Command, если они есть
})
public abstract class Command implements Serializable {

    private String title;
    private String description;
    private CommandType type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }
}
