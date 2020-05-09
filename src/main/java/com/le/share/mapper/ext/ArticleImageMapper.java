package com.le.share.mapper.ext;

import com.le.share.model.ArticleImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleImageMapper {

    /**
     * 批量保存.
     * @param images
     * @return
     */
    int multiInsert(@Param("images") List<ArticleImage> images);

    /**
     * 删除图片信息.
     * @param artId
     * @return
     */
    int deleteByArtcleId(Long artId);

    /**
     * 获取图片地址列表.
     * @param artId
     * @param limit
     * @return
     */
    List<String> selectByArticleId(@Param("artId") Long artId, @Param("limit") Integer limit);

    /**
     * 根据文章ID获取图片信息.
     * @param artIds
     * @return
     */
    List<ArticleImage> selectByArtIds(List<Long> artIds);

    int deleteByPrimaryKey(Long id);

    int insert(ArticleImage record);

    int insertSelective(ArticleImage record);

    ArticleImage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleImage record);

    int updateByPrimaryKey(ArticleImage record);
}