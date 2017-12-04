package com.bstsnail.vaadin

import com.vaadin.annotations.*
import com.vaadin.server.FontAwesome
import com.vaadin.server.VaadinRequest
import com.vaadin.server.VaadinServlet
import com.vaadin.shared.ui.ValueChangeMode
import com.vaadin.shared.ui.ui.Transport
import com.vaadin.ui.*
import com.vaadin.ui.themes.ValoTheme
import java.time.LocalDate
import javax.servlet.annotation.WebServlet

/**
 * rdeng
 * 04/12/2017
 */
@Theme("valo")
@Title("Vaadin Gradle Example")
@Push(transport = Transport.WEBSOCKET_XHR)
@Viewport("width=device-width, initial-scale=1.0")
class CustomerUI: UI() {

    private val filterText = TextField()
    private val grid = Grid<Customer>(Customer::class.java)
    private val form = CustomerForm(this)

    override fun init(request: VaadinRequest?) {
        val layout = VerticalLayout()

        filterText.placeholder = "filter by name..."
        filterText.valueChangeMode = ValueChangeMode.LAZY
        filterText.addValueChangeListener {
            updateList()
        }

        val clearFilterTextBtn = Button(FontAwesome.TIMES)
        clearFilterTextBtn.description = "clear the current filter"
        clearFilterTextBtn.addClickListener {
            filterText.clear()
        }

        val filtering = CssLayout()
        filtering.addComponents(filterText, clearFilterTextBtn)
        filtering.styleName = ValoTheme.LAYOUT_COMPONENT_GROUP

        val addCustomButton = Button("Add new customer")

        addCustomButton.addClickListener {
            grid.asSingleSelect().clear()
            form.setCustomer(Customer(1, "", "", LocalDate.now(), CustomerStatus.NotContacted, ""))
        }

        val toolBar = HorizontalLayout(filtering, addCustomButton)

        grid.setColumns("firstName", "lastName", "email")
        grid.setSizeFull()

        val main = HorizontalLayout(grid, form)
        main.setSizeFull()
        main.setExpandRatio(grid, 1f)


        layout.addComponents(toolBar, main)
        updateList()
        form.isVisible = false
        content = layout

        grid.asSingleSelect().addValueChangeListener {
            if (it.value == null) {
                form.isVisible = false
            }
            else {
                form.setCustomer(it.value)
            }
        }
    }

    fun updateList() {
        grid.setItems(CustomerService.findAll())
    }

    @WebServlet(urlPatterns = arrayOf("/*"), name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CustomerUI::class, productionMode = false)
    class MyUIServlet : VaadinServlet()
}