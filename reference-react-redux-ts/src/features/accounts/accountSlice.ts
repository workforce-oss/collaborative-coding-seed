// Path: src/features/accounts/accountSlice.ts

import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { Account } from './accountApi';


export interface AccountState {
    accounts: Account[];
    status: 'idle' | 'loading' | 'failed';
}

const initialState: AccountState = {
    accounts: new Array<Account>(),
    status: 'idle',
};

export const accountSlice = createSlice({
    name: 'accounts',
    initialState,
    reducers: {
        setAccounts: (state, action: PayloadAction<Account[]>) => {
            state.accounts = action.payload;
        },
        addAccount: (state, action: PayloadAction<Account>) => {
            state.accounts.push(action.payload);
        },
        deleteAccount: (state, action: PayloadAction<number>) => {
            state.accounts = state.accounts.filter(account => account.id !== action.payload);
        },
        updateAccount: (state, action: PayloadAction<Account>) => {
            state.accounts = state.accounts.map(account => account.id === action.payload.id ? action.payload : account);
        }
    },
});

export const { setAccounts, addAccount, deleteAccount, updateAccount } = accountSlice.actions;

export default accountSlice.reducer;

