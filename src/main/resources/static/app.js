const P = '/api/products';
const S = '/api/suppliers';

let products = [];
let suppliers = [];

const $ = id => document.getElementById(id);

const esc = value =>
    String(value ?? '').replace(
        /[&<>"']/g,
        character => ({
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            '"': '&quot;',
            "'": '&#39;'
        }[character])
    );

function msg(text, type = 'success') {

    const element = $('message');

    element.textContent = text;
    element.className = 'message ' + type;

    setTimeout(() => {
        element.textContent = '';
        element.className = '';
    }, 3500);
}

async function request(url, options) {

    const response = await fetch(url, options);

    if (!response.ok) {

        let error = {};

        try {
            error = await response.json();
        } catch {
        }

        throw new Error(
            error.message || 'Request failed'
        );
    }

    if (response.status === 204) {
        return null;
    }

    return response.json();
}

/* ================= NAVIGATION ================= */

document
    .querySelectorAll('.tab')
    .forEach(button => {

        button.onclick = () => {

            document
                .querySelectorAll('.tab,.page')
                .forEach(element =>
                    element.classList.remove('active')
                );

            button.classList.add('active');

            $(button.dataset.page)
                .classList.add('active');
        };
    });

/* ================= LOAD DATA ================= */

async function loadSuppliers() {

    suppliers = await request(S);

    renderSuppliers();
}

async function loadProducts() {

    products = await request(P);

    renderProducts();
}

/* ================= PRODUCTS ================= */

function renderProducts() {

    const search =
        $('product-search')
            .value
            .toLowerCase();

    const filtered = products.filter(product =>
        JSON.stringify(product)
            .toLowerCase()
            .includes(search)
    );

    $('product-body').innerHTML =
        filtered.map(product => `

            <tr>

                <td>
                    ${esc(product.barcode)}
                </td>

                <td>
                    ${esc(product.name)}
                </td>

                <td>
                    ${esc(product.category)}
                </td>

                <td>
                    ${esc(product.brand)}
                </td>

                <td>
                    ${Number(product.purchasePrice ?? 0).toFixed(2)}
                </td>

                <td>
                    ${Number(product.sellingPrice ?? 0).toFixed(2)}
                </td>

                <td>
                    ${esc(product.status)}
                </td>

                <td>

                    <button
                        class="edit"
                        onclick="editProduct(${product.id})">

                        Edit

                    </button>

                    <button
                        class="delete"
                        onclick="deleteProduct(${product.id})">

                        Delete

                    </button>

                </td>

            </tr>

        `).join('')

        ||

        `
            <tr>
                <td colspan="8">
                    No products
                </td>
            </tr>
        `;
}

function resetProduct() {

    $('product-form').reset();

    $('product-id').value = '';

    $('product-status').value = 'ACTIVE';

    $('product-title').textContent =
        'Add Product';

    $('product-cancel').hidden = true;
}

window.editProduct = id => {

    const product =
        products.find(item => item.id === id);

    if (!product) {
        return;
    }

    $('product-id').value =
        product.id;

    $('barcode').value =
        product.barcode ?? '';

    $('product-name').value =
        product.name ?? '';

    $('category').value =
        product.category ?? '';

    $('brand').value =
        product.brand ?? '';

    $('size').value =
        product.size ?? '';

    $('color').value =
        product.color ?? '';

    $('purchase-price').value =
        product.purchasePrice ?? '';

    $('selling-price').value =
        product.sellingPrice ?? '';

    $('product-status').value =
        product.status ?? 'ACTIVE';

    $('product-title').textContent =
        'Edit Product';

    $('product-cancel').hidden = false;

    window.scrollTo(0, 0);
};

window.deleteProduct = async id => {

    if (!confirm('Delete this product?')) {
        return;
    }

    try {

        await request(
            `${P}/${id}`,
            {
                method: 'DELETE'
            }
        );

        await loadProducts();

        msg('Product deleted');

    } catch (error) {

        msg(
            error.message,
            'error'
        );
    }
};

$('product-form').onsubmit =
    async event => {

        event.preventDefault();

        const id =
            $('product-id').value;

        const payload = {

            barcode:
                $('barcode')
                    .value
                    .trim(),

            name:
                $('product-name')
                    .value
                    .trim(),

            category:
                $('category')
                    .value
                    .trim(),

            brand:
                $('brand')
                    .value
                    .trim(),

            size:
                $('size')
                    .value
                    .trim(),

            color:
                $('color')
                    .value
                    .trim(),

            purchasePrice:
                Number(
                    $('purchase-price').value
                ),

            sellingPrice:
                Number(
                    $('selling-price').value
                ),

            status:
                $('product-status').value
        };

        try {

            await request(
                id
                    ? `${P}/${id}`
                    : P,
                {
                    method:
                        id
                            ? 'PUT'
                            : 'POST',

                    headers: {
                        'Content-Type':
                            'application/json'
                    },

                    body:
                        JSON.stringify(payload)
                }
            );

            resetProduct();

            await loadProducts();

            msg('Product saved');

        } catch (error) {

            msg(
                error.message,
                'error'
            );
        }
    };

/* ================= SUPPLIERS ================= */

function renderSuppliers() {

    const search =
        $('supplier-search')
            .value
            .toLowerCase();

    const filtered =
        suppliers.filter(supplier =>
            JSON.stringify(supplier)
                .toLowerCase()
                .includes(search)
        );

    $('supplier-body').innerHTML =
        filtered.map(supplier => `

            <tr>

                <td>
                    ${esc(supplier.name)}
                </td>

                <td>
                    ${esc(supplier.contactPerson)}
                </td>

                <td>
                    ${esc(supplier.email)}
                </td>

                <td>
                    ${esc(supplier.phone)}
                </td>

                <td>
                    ${esc(supplier.address)}
                </td>

                <td>
                    ${esc(supplier.status)}
                </td>

                <td>

                    <button
                        class="edit"
                        onclick="editSupplier(${supplier.id})">

                        Edit

                    </button>

                    <button
                        class="delete"
                        onclick="deleteSupplier(${supplier.id})">

                        Delete

                    </button>

                </td>

            </tr>

        `).join('')

        ||

        `
            <tr>
                <td colspan="7">
                    No suppliers
                </td>
            </tr>
        `;
}

function resetSupplier() {

    $('supplier-form').reset();

    $('supplier-id').value = '';

    $('supplier-status').value =
        'ACTIVE';

    $('supplier-title').textContent =
        'Add Supplier';

    $('supplier-cancel').hidden = true;
}

window.editSupplier = id => {

    const supplier =
        suppliers.find(item => item.id === id);

    if (!supplier) {
        return;
    }

    $('supplier-id').value =
        supplier.id;

    $('supplier-name').value =
        supplier.name ?? '';

    $('contact-person').value =
        supplier.contactPerson ?? '';

    $('email').value =
        supplier.email ?? '';

    $('phone').value =
        supplier.phone ?? '';

    $('address').value =
        supplier.address ?? '';

    $('bank-details').value =
        supplier.bankDetails ?? '';

    $('supplier-status').value =
        supplier.status ?? 'ACTIVE';

    $('supplier-title').textContent =
        'Edit Supplier';

    $('supplier-cancel').hidden =
        false;

    window.scrollTo(0, 0);
};

window.deleteSupplier = async id => {

    if (!confirm('Delete this supplier?')) {
        return;
    }

    try {

        await request(
            `${S}/${id}`,
            {
                method: 'DELETE'
            }
        );

        await loadSuppliers();

        msg('Supplier deleted');

    } catch (error) {

        msg(
            error.message,
            'error'
        );
    }
};

$('supplier-form').onsubmit =
    async event => {

        event.preventDefault();

        const id =
            $('supplier-id').value;

        const payload = {

            name:
                $('supplier-name')
                    .value
                    .trim(),

            contactPerson:
                $('contact-person')
                    .value
                    .trim(),

            email:
                $('email')
                    .value
                    .trim(),

            phone:
                $('phone')
                    .value
                    .trim(),

            address:
                $('address')
                    .value
                    .trim(),

            bankDetails:
                $('bank-details')
                    .value
                    .trim(),

            status:
                $('supplier-status').value
        };

        try {

            await request(
                id
                    ? `${S}/${id}`
                    : S,
                {
                    method:
                        id
                            ? 'PUT'
                            : 'POST',

                    headers: {
                        'Content-Type':
                            'application/json'
                    },

                    body:
                        JSON.stringify(payload)
                }
            );

            resetSupplier();

            await loadSuppliers();

            msg('Supplier saved');

        } catch (error) {

            msg(
                error.message,
                'error'
            );
        }
    };

/* ================= SEARCH ================= */

$('product-search').oninput =
    renderProducts;

$('supplier-search').oninput =
    renderSuppliers;

/* ================= CANCEL BUTTONS ================= */

$('product-cancel').onclick =
    resetProduct;

$('supplier-cancel').onclick =
    resetSupplier;

/* ================= BARCODE ================= */

$('scan-input').onkeydown =
    async event => {

        if (event.key !== 'Enter') {
            return;
        }

        event.preventDefault();

        const code =
            event.target.value.trim();

        event.target.value = '';

        if (!code) {
            return;
        }

        try {

            const product =
                await request(
                    `${P}/barcode/${encodeURIComponent(code)}`
                );

            editProduct(product.id);

            $('scan-status').textContent =
                'Found: ' + product.name;

        } catch {

            $('barcode').value = code;

            $('scan-status').textContent =
                'Not found - add new product';
        }
    };

/* ================= INITIAL LOAD ================= */

Promise
    .all([
        loadSuppliers(),
        loadProducts()
    ])
    .catch(error =>
        msg(
            error.message,
            'error'
        )
    );