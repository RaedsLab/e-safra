package services.interfaces;

import java.util.List;

import javax.ejb.Remote;

import domain.Line;

@Remote
public interface LineServicesRemote {

	Boolean addLine(Line Line);

	Boolean deleteLine(Line Line);

	Boolean deleteLineById(Integer id);

	Boolean updateLine(Line Line);

	Line findLineById(Integer id);

	List<Line> findAllLines();
}
