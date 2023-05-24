package core.api.models.DTOs;

import java.util.List;

import core.api.models.Collection;

public class SeriesDto extends Collection<SerieDto> {
    public List<SerieDto> getSeries() {
        return this.getResults();
    }
}
