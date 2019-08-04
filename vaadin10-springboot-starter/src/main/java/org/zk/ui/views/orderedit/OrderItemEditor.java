package org.zk.ui.views.orderedit;

import java.util.Objects;
import java.util.stream.Stream;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.internal.AbstractFieldSupport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.zk.backend.data.entity.OrderItem;
import org.zk.backend.data.entity.Product;
import org.zk.ui.components.AmountField;
import org.zk.ui.dataproviders.ProductDataProvider;
import org.zk.ui.utils.FormattingUtils;
import org.zk.ui.views.storefront.events.CommentChangeEvent;
import org.zk.ui.views.storefront.events.DeleteEvent;
import org.zk.ui.views.storefront.events.PriceChangeEvent;
import org.zk.ui.views.storefront.events.ProductChangeEvent;

@Tag("order-item-editor")
@HtmlImport("src/views/orderedit/order-item-editor.html")
public class OrderItemEditor extends PolymerTemplate<TemplateModel> implements HasValueAndElement<ComponentValueChangeEvent<OrderItemEditor,OrderItem>, OrderItem> {

	@Id("products")
	private ComboBox<Product> products;

	@Id("delete")
	private Button delete;

	@Id("amount")
	private AmountField amount;

	@Id("price")
	private Div price;

	@Id("comment")
	private TextField comment;

	private int totalPrice;
	
    private final AbstractFieldSupport<OrderItemEditor,OrderItem> fieldSupport;

	private BeanValidationBinder<OrderItem> binder = new BeanValidationBinder<>(OrderItem.class);
	public OrderItemEditor(ProductDataProvider productSource) {
		this.fieldSupport =  new AbstractFieldSupport<>(this, null,
				Objects::equals, c ->  {});
		products.setDataProvider(productSource);
		products.addValueChangeListener(e -> {
			setPrice();
			fireEvent(new ProductChangeEvent(this, e.getValue()));
		});
		amount.addValueChangeListener(e -> setPrice());
		comment.addValueChangeListener(e -> fireEvent(new CommentChangeEvent(this, e.getValue())));

		binder.forField(amount).bind("quantity");
		amount.setRequiredIndicatorVisible(true);
		binder.forField(comment).bind("comment");
		binder.forField(products).bind("product");
		products.setRequired(true);

		delete.addClickListener(e -> fireEvent(new DeleteEvent(this)));
		setPrice();
	}
	
	private void setPrice() {
		int oldValue = totalPrice;
		Integer selectedAmount = amount.getValue();
		Product product = products.getValue();
		totalPrice = 0;
		if (selectedAmount != null && product != null) {
			totalPrice = selectedAmount * product.getPrice();
		}
		price.setText(FormattingUtils.formatAsCurrency(totalPrice));
		if (oldValue != totalPrice) {
			fireEvent(new PriceChangeEvent(this, oldValue, totalPrice));
		}
	}

	@Override
	public void setValue(OrderItem value) {
		fieldSupport.setValue(value);
		binder.setBean(value);
		boolean noProductSelected = value == null || value.getProduct() == null;
		amount.setEnabled(!noProductSelected);
		delete.setEnabled(!noProductSelected);
		comment.setEnabled(!noProductSelected);
		setPrice();
	}

	@Override
	public OrderItem getValue() {
		return fieldSupport.getValue();
	}

	public Stream<HasValue<?, ?>> validate() {
		return binder.validate().getFieldValidationErrors().stream().map(BindingValidationStatus::getField);
	}

	public Registration addPriceChangeListener(ComponentEventListener<PriceChangeEvent> listener) {
		return addListener(PriceChangeEvent.class, listener);
	}

	public Registration addProductChangeListener(ComponentEventListener<ProductChangeEvent> listener) {
		return addListener(ProductChangeEvent.class, listener);
	}

	public Registration addCommentChangeListener(ComponentEventListener<CommentChangeEvent> listener) {
		return addListener(CommentChangeEvent.class, listener);
	}

	public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
		return addListener(DeleteEvent.class, listener);
	}

	@Override
	public Registration addValueChangeListener(
			ValueChangeListener<? super ComponentValueChangeEvent<OrderItemEditor, OrderItem>> listener) {
		return fieldSupport.addValueChangeListener(listener);
	}

}
