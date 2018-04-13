package com.opengroup.longmao.gwcommon.repository.slave;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.PhotoAlbum;

/**
 * 【用户登录记录】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年04月24日 下午18:19:03
 */
public interface PhotoAlbumRepositorySlave extends BaseRepository<PhotoAlbum, Long> {

	@Query("SELECT p FROM PhotoAlbum p WHERE p.userId = ?1 AND p.photoType = ?2")
	List<PhotoAlbum> getPhotoByUserIdOrType(String userId, Integer picType);
}
