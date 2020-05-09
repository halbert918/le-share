package com.le.share.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinbohe.
 * Date 2019/8/21
 * Description
 */
@ApiModel(value = "分页对象", description = "PageResponseData")
public class Pager<T> {

  public static final Integer DEFAULT_PAGE_NUM = 20;                                // 默认每页记录数

  @ApiModelProperty(value = "每页记录数", dataType = "long")
  private int size = DEFAULT_PAGE_NUM;

  @ApiModelProperty(value = "当前页", dataType = "long")
  private int page = 1;

  @ApiModelProperty(value = "总页数", dataType = "long")
  private int pageCount;

  @ApiModelProperty(value = "总记录数", dataType = "long")
  private long totalCount = 0;

  @ApiModelProperty(value = "数据", dataType = "List")
  private List<T> data = new ArrayList<>();

  public Pager() {
  }

  /**
   * .
   * @param page 页码
   * @param totalCount 总数
   * @param data 数据
   */
  public Pager(int page, long totalCount, List<T> data) {
    this.page = page;
    this.totalCount = totalCount;
    this.data = data;
    if (totalCount > 0L) {
      pageCount = (int) (totalCount / size);
      if (totalCount % size > 0) {
        pageCount++;
      }
    }
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getPageCount() {
    return pageCount;
  }

  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }

  public long getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(long totalCount) {
    this.totalCount = totalCount;
  }

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }
}
