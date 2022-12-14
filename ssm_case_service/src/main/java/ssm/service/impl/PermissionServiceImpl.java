package ssm.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssm.dao.IPermissionDao;
import ssm.domain.Permission;
import ssm.service.IPermissionServive;

import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionServive {
    @Autowired
    private IPermissionDao permissionDao;

    @Override
    public List<Permission> findAll(int page,int size) throws Exception {
        PageHelper.startPage(page,size);
        return permissionDao.findAll();
    }

    @Override
    public void save(Permission permission) throws Exception {
        permissionDao.save(permission);
    }

    @Override
    public void deleteRole_permissionById(Integer id) {
        permissionDao.deleteRole_permissionById(id);
    }

    @Override
    public void deletePermissionById(Integer id) {
        permissionDao.deletePermissionById(id);
    }

    @Override
    public Permission findById(Integer id) throws Exception {
        return permissionDao.findById(id);
    }
}
