package nova.publish.bazarbooks.core.domain.repository

import nova.publish.bazarbooks.core.domain.model.Address

interface AddressRepository {
    suspend fun getSelectedAddress(): Address?
    suspend fun saveSelectedAddress(address: Address): Address
}
