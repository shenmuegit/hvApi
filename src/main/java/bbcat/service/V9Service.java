package bbcat.service;

import bbcat.entity.V9;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface V9Service {

    IPage<V9> selectV9List(Long pageCount);
}
