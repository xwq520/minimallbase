package com.minimall.boilerplate.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

import static com.minimall.boilerplate.common.Constants.HTTP_HEADER_PAGINATION_INDEX;
import static com.minimall.boilerplate.common.Constants.HTTP_HEADER_PAGINATION_SIZE;
import static java.util.Objects.isNull;

/**
 * Title: .
 * <p>Description: </p>

 */
public final class BusinessHelper {

  private BusinessHelper() {
  }

  public static class PageableConverter {

    private PageableConverter() {
    }

    public static Pageable toPageable(Map<String, String> requestHeader) {
      Sort sort = new Sort(Sort.Direction.DESC, "id");
      return toPageable(requestHeader, sort);
    }

    public static Pageable toPageable(Map<String, String> requestHeader, Sort sort) {
      if((isNull(requestHeader.get(HTTP_HEADER_PAGINATION_INDEX)))
              || isNull(requestHeader.get(HTTP_HEADER_PAGINATION_SIZE)))
        return null;
      int page = Integer.valueOf(requestHeader.get(HTTP_HEADER_PAGINATION_INDEX));
      int size = Integer.valueOf(requestHeader.get(HTTP_HEADER_PAGINATION_SIZE));
      return new PageRequest(page, size, sort);
    }
  }
}
