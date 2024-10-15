// Path: src/features/accounts/accountManager.ts

import { Account, AccountApi } from './accountApi';
import { AppThunk, RootState } from '../../app/store';
import * as accountSlice from './accountSlice';

const accountApi = new AccountApi('http://localhost:8080');

export const selectAccounts = (state: RootState) => state.accounts.accounts;

export const fetchAccounts = (): AppThunk => async dispatch => {
    const accounts = await accountApi.getAllAccounts();
    console.log(accounts);
    dispatch(accountSlice.setAccounts(accounts));
}

export const fetchAccount = (id: number): AppThunk => async dispatch => {
    const account = await accountApi.getAccount(id);
    dispatch(accountSlice.updateAccount(account));
}

export const createAccount = (account: Account): AppThunk => async dispatch => {
    const newAccount = await accountApi.createAccount(account);
    dispatch(accountSlice.addAccount(newAccount));
}

export const updateAccount = (account: Account): AppThunk => async dispatch => {
    const updatedAccount = await accountApi.updateAccount(account);
    dispatch(accountSlice.updateAccount(updatedAccount));
}

export const deleteAccount = (id: number): AppThunk => async dispatch => {
    await accountApi.deleteAccount(id);
    dispatch(accountSlice.deleteAccount(id));
}