package carrental.repository;

import carrental.domain.PassportInfo;

public interface PassportInfoDao {
	PassportInfo find(Integer userId);
	void create(PassportInfo passportInfo);
	void update(PassportInfo passportInfo);
	void delete(Integer userId);
}
