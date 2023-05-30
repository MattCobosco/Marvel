package core.api.models.DTOs;

import java.util.List;

import core.api.models.Collection;

public class CreatorsDto extends Collection<CreatorDto> {
    public List<CreatorDto> getCreators() {
        return this.getResults();
    }
}
