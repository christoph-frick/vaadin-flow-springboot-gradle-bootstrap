import {html, LitElement} from 'lit-element';
import '@vaadin/vaadin-grid';
import '@vaadin/vaadin-checkbox';
import '@vaadin/vaadin-text-field';
import '@vaadin/vaadin-select';

class EditGrid extends LitElement {

    static get properties() {
        return {
            itmes: {tyle: Array},
            roles: {type: Array}
        }
    }

    constructor() {
        super();
        this.roles = ['singer', 'drums', 'lead guitar']
        this.items = [
            {"alive": false, "name": "John", "role": "singer"},
            {"alive": true, "name": "Ringo", "role": "drums"}
        ]
    }

    render() {
        return html`
            <vaadin-grid
                    id="grid"
                    items='${JSON.stringify(this.items)}'
                    style="height: 100%"
            >
                <vaadin-grid-column>
                    <template class="header">Alive</template>
                    <template>
                        <vaadin-checkbox
                                checked="[[item.alive]]"
                        />
                    </template>
                </vaadin-grid-column>
                <vaadin-grid-column>
                    <template class="header">Name</template>
                    <template>
                        <vaadin-text-field
                                value="[[item.name]]"
                        />
                    </template>
                </vaadin-grid-column>
                <vaadin-grid-column>
                    <template class="header">Role</template>
                    <template>
                        <vaadin-select value="[[item.role]]">
                            <template>
                                <vaadin-list-box>
                                    ${this.roles.map(role => html`
                                        <vaadin-item>${role}</vaadin-item>
                                    `)}
                                </vaadin-list-box>
                            </template>
                        </vaadin-select>
                    </template>
                </vaadin-grid-column>
            </vaadin-grid>
        `;
    }
}

customElements.define('edit-grid', EditGrid);