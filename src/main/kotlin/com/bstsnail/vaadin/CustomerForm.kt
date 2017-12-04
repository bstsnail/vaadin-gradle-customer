package com.bstsnail.vaadin

import com.vaadin.data.Binder
import com.vaadin.event.ShortcutAction
import com.vaadin.ui.*
import com.vaadin.ui.themes.ValoTheme

/**
 * rdeng
 * 02/10/2017
 */
class CustomerForm(val ui: CustomerUI): FormLayout() {

    private val save = Button("Save")
    private val delete = Button("Delete")

    private val firstName = TextField("First Name")
    private val lastName = TextField("Last Name")
    private val email = TextField("Email")
    private val status = NativeSelect<CustomerStatus>("Status")
    private val birthDate = DateField("Birthday")

    private val binder = Binder<Customer>(Customer::class.java)

    private var customer: Customer? = null


    init {
        setSizeUndefined()
        val buttons = HorizontalLayout(save, delete)
        addComponents(firstName, lastName, email, status, birthDate, buttons)

        val list = ArrayList<CustomerStatus>()
        CustomerStatus.values().forEach {
            list.add(it)
        }
        status.setItems(list)

        save.styleName = ValoTheme.BUTTON_PRIMARY
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER)

        binder.bindInstanceFields(this)

        save.addClickListener {
            save()
            ui.updateList()
            isVisible = false
        }

        delete.addClickListener {
            delete()
            ui.updateList()
            isVisible = false
        }
    }

    fun setCustomer(customer: Customer) {
        this.customer = customer
        binder.bean = customer
        isVisible = true

    }
    private fun save() {
        CustomerService.save(customer!!)
    }

    private fun delete() {
        CustomerService.delete(customer!!.id)
    }
}