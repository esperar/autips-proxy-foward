package esperer.otp.principal.usecase

import esperer.otp.annotation.TransactionalUseCase
import esperer.otp.persistence.OtpRepository
import esperer.otp.persistence.UserRepository
import esperer.otp.principal.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder

@TransactionalUseCase
class UserUseCase(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val otpRepository: OtpRepository
) {

    fun addUser(user: User) {
        user.password = passwordEncoder.encode(user.password)
    }

    fun auth(user: User){
        val findUser = userRepository.findByIdOrNull(user.username)
            ?: throw RuntimeException("Not Found User.")

        if(passwordEncoder.matches(user.password, findUser.password))
            throw RuntimeException("Password Mismatch..")

        renewOtp(findUser)
    }

    private fun renewOtp(user: User) {

    }

}