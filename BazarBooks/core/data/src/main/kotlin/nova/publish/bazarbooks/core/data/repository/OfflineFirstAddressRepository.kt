package nova.publish.bazarbooks.core.data.repository

import nova.publish.bazarbooks.core.data.local.dao.FigmaMetadataDao
import nova.publish.bazarbooks.core.data.local.entity.AddressEntity
import nova.publish.bazarbooks.core.domain.model.Address
import nova.publish.bazarbooks.core.domain.repository.AddressRepository

class OfflineFirstAddressRepository(
    private val figmaMetadataDao: FigmaMetadataDao,
) : AddressRepository {
    override suspend fun getSelectedAddress(): Address? = figmaMetadataDao.getAddress(SelectedAddressId)?.toDomain()

    override suspend fun saveSelectedAddress(address: Address): Address {
        figmaMetadataDao.upsertAddresses(listOf(address.toEntity()))
        return address
    }

    private fun Address.toEntity() = AddressEntity(
        id = SelectedAddressId,
        recipient = recipient,
        phone = phone,
        governorate = region,
        city = city,
        block = block,
        street = street.ifBlank { line1 },
        building = building,
        floor = floor,
        flat = flat,
        avenue = avenue,
        label = "Home",
    )

    private fun AddressEntity.toDomain() = Address(
        recipient = recipient,
        phone = phone,
        line1 = listOf(street, building).filter { it.isNotBlank() }.joinToString(", "),
        line2 = listOf(block, floor, flat, avenue).filterNotNull().filter { it.isNotBlank() }.joinToString(", ").ifBlank { null },
        city = city,
        region = governorate,
        block = block,
        street = street,
        building = building,
        floor = floor,
        flat = flat,
        avenue = avenue,
    )

    private companion object {
        const val SelectedAddressId = "selected-home-address"
    }
}
