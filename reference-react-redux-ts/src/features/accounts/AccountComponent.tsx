// Path: src/features/accounts/AccountComponent.tsx

import React, { useState, useEffect } from 'react';
import { useAppSelector, useAppDispatch } from '../../app/hooks';
import { selectAccounts, fetchAccounts, fetchAccount, createAccount, updateAccount, deleteAccount } from './accountManager';
import { Account, AccountStatus } from './accountApi'
import { styled } from '@mui/material/styles';

import { Button, ButtonGroup, Container, Grid, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField } from '@mui/material';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { parseISO } from 'date-fns';

const RootAccountComponent = styled('div')(({ theme }) => ({
    flexGrow: 1,
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));

const AccountPaper = styled(Paper)(({ theme }) => ({
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));

export default function AccountComponent() {
    const accounts = useAppSelector(selectAccounts);
    const dispatch = useAppDispatch();
    const [account, setAccount] = useState<Account>({
        id: 0,
        name: '',
        number: '',
        openDate: '',
        closeDate: '',
        status: AccountStatus.OPEN
    });

    useEffect(() => {
        dispatch(fetchAccounts());
    }, [dispatch]);

    const handleCreate = () => {
        dispatch(createAccount(account));
    }

    const handleUpdate = () => {
        dispatch(updateAccount(account));
    }

    const handleDelete = () => {
        dispatch(deleteAccount(account.id));
    }

    const handleSelect = (id: number) => {
        dispatch(fetchAccount(id));
        setAccount(accounts.find(a => a.id === id) ?? account);
    }

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setAccount({ ...account, [name]: value });
    }

    return (
        <RootAccountComponent>
            <LocalizationProvider dateAdapter={AdapterDateFns}>
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <AccountPaper>
                            <Container maxWidth="sm">
                                <Grid container spacing={2}>
                                    <Grid item xs={12}>
                                        <TextField
                                            fullWidth
                                            id="id"
                                            label="Id"
                                            name="id"
                                            disabled
                                            value={account.id}
                                        />
                                    </Grid>
                                    <Grid item xs={12}>
                                        <TextField
                                            fullWidth
                                            id="name"
                                            label="Name"
                                            name="name"
                                            onChange={handleInputChange}
                                            value={account.name}
                                        />
                                    </Grid>
                                    <Grid item xs={12}>
                                        <TextField
                                            fullWidth
                                            id="number"
                                            label="Number"
                                            name="number"
                                            onChange={handleInputChange}
                                            value={account.number}
                                        />
                                    </Grid>
                                    <Grid item xs={12}>
                                        <DatePicker
                                            key="openDate"
                                            label="Open Date"
                                            onChange={(newValue) => {
                                                console.log(newValue);
                                                setAccount({ ...account, openDate: newValue?.toISOString() ?? '' });
                                            }}
                                            value={parseISO(account.openDate)}
                                        />
                                    </Grid>
                                    <Grid item xs={12}>
                                        <DatePicker
                                            key="closeDate"
                                            label="Close Date"
                                            onChange={(newValue) => {
                                                console.log(newValue);
                                                setAccount({ ...account, closeDate: newValue?.toISOString() ?? '' });
                                            }}
                                            value={parseISO(account.closeDate)}
                                        />
                                    </Grid>
                                    <Grid item xs={12}>
                                        <TextField
                                            fullWidth
                                            id="status"
                                            label="Status"
                                            name="status"
                                            onChange={handleInputChange}
                                            value={account.status}

                                        />
                                    </Grid>
                                    <Grid item xs={12}>
                                        <ButtonGroup variant="contained" color="primary" aria-label="contained primary button group">
                                            <Button onClick={handleCreate}>
                                                Create
                                            </Button>
                                            <Button onClick={handleUpdate}>
                                                Update
                                            </Button>
                                            <Button onClick={handleDelete}>
                                                Delete
                                            </Button>
                                        </ButtonGroup>
                                    </Grid>
                                </Grid>
                            </Container>
                        </AccountPaper>
                    </Grid>
                    <Grid item xs={12}>
                        <AccountPaper>
                            <TableContainer component={Paper}>
                                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                                    <TableHead>
                                        <TableRow>
                                            <TableCell>
                                                ID
                                            </TableCell>
                                            <TableCell align="right">
                                                Name
                                            </TableCell>
                                            <TableCell align="right">
                                                Number
                                            </TableCell>
                                            <TableCell align="right">
                                                Open Date
                                            </TableCell>
                                            <TableCell align="right">
                                                Close Date
                                            </TableCell>
                                            <TableCell align="right">
                                                Status
                                            </TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {accounts?.map((account) => (
                                            <TableRow
                                                key={account.id}
                                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                                onClick={() => handleSelect(account.id)}
                                            >
                                                <TableCell component="th" scope="row">
                                                    {account.id}
                                                </TableCell>
                                                <TableCell align="right">
                                                    {account.name}
                                                </TableCell>
                                                <TableCell align="right">
                                                    {account.number}
                                                </TableCell>
                                                <TableCell align="right">
                                                    {account.openDate}
                                                </TableCell>
                                                <TableCell align="right">
                                                    {account.closeDate}
                                                </TableCell>
                                                <TableCell align="right">
                                                    {account.status}
                                                </TableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </AccountPaper>
                    </Grid>
                </Grid>
            </LocalizationProvider>
        </RootAccountComponent>
    );
}
