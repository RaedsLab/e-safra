package services.interfaces;

import java.util.List;

import javax.ejb.Local;

import domain.Line;

@Local
public interface LineServicesLocal {
	
	Boolean addLine(Line Line);

	Boolean deleteLine(Line Line);

	Boolean deleteLineById(Integer id);

	Boolean updateLine(Line Line);

	Line findLineById(Integer id);

	List<Line> findAllLines();

}
