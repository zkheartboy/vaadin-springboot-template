package org.zk.ui.views.admin.product;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import org.zk.backend.data.entity.Product;
import org.zk.backend.service.ProductService;
import org.zk.ui.navigation.NavigationManager;
import org.zk.ui.views.admin.AbstractCrudPresenter;

@SpringComponent
@ViewScope
public class ProductAdminPresenter extends AbstractCrudPresenter<Product, ProductService, ProductAdminView> {

	@Autowired
	public ProductAdminPresenter(ProductAdminDataProvider productAdminDataProvider, NavigationManager navigationManager,
			ProductService service, BeanFactory beanFactory) {
		super(navigationManager, service, Product.class, productAdminDataProvider, beanFactory);
	}
}
