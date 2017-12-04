package com.bstsnail.vaadin

/**
 * rdeng
 * 03/10/2017
 */
object CustomerService {

    private val contacts = HashMap<Long, Customer>()

    fun findAll(): List<Customer> {
        return findAll(null)
    }
    fun findAll(filterString: String?): List<Customer> {
        val ret = ArrayList<Customer>()
        contacts.forEach {
            if (filterString.isNullOrEmpty()) {
                ret.add(it.value)
            }
            else {
                if(it.value.firstName.contains(filterString!!) ||
                        it.value.lastName.contains(filterString)) {
                    ret.add(it.value)
                }
            }
        }
        return ret
    }

    fun save(customer: Customer) {
        contacts.put(customer.id, customer)
    }

    fun delete(id: Long) {
        contacts.remove(id)
    }
}