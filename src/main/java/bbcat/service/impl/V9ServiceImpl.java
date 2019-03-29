package bbcat.service.impl;

import bbcat.entity.V9;
import bbcat.mapper.V9Mapper;
import bbcat.service.V9Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class V9ServiceImpl implements V9Service {

    @Autowired
    private V9Mapper v9m;

    @Override
    public IPage<V9> selectV9List(Long pageCount) {
        Page<V9> page = new Page<>();
        page.setCurrent(pageCount);
        page.setSize(60);
        page.setDesc("id");
        return v9m.selectPage(page,null);
    }
}
