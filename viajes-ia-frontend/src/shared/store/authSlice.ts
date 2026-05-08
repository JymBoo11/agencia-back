import { createSlice, PayloadAction } from '@reduxjs/toolkit'

interface AuthState {
  token: string | null
  email: string | null
  userId: number | null
}

const initialState: AuthState = {
  token: localStorage.getItem('token'),
  email: localStorage.getItem('email'),
  userId: localStorage.getItem('userId') ? parseInt(localStorage.getItem('userId')!) : null
}

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    setCredentials: (state, action: PayloadAction<{ token: string; email: string; userId: number }>) => {
      state.token = action.payload.token
      state.email = action.payload.email
      state.userId = action.payload.userId
      localStorage.setItem('token', action.payload.token)
      localStorage.setItem('email', action.payload.email)
      localStorage.setItem('userId', action.payload.userId.toString())
    },
    logout: (state) => {
      state.token = null
      state.email = null
      state.userId = null
      localStorage.removeItem('token')
      localStorage.removeItem('email')
      localStorage.removeItem('userId')
    }
  }
})

export const { setCredentials, logout } = authSlice.actions
export default authSlice.reducer