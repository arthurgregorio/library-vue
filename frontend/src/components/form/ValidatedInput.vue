<template>
  <ValidationProvider
    :vid="vid"
    :name="$attrs.name || $attrs.label"
    :rules="rules"
    v-slot="{ errors, valid }">
    <b-field
      v-bind="$attrs"
      :type="{ 'is-danger': errors[0], 'is-success': valid }"
      :message="errors">
      <b-input v-model="innerValue" v-bind="$attrs"></b-input>
    </b-field>
  </ValidationProvider>
</template>

<script lang="ts">
import { ValidationProvider } from 'vee-validate'
import {
  Vue,
  Prop,
  Watch,
  Component
} from 'vue-property-decorator'

@Component({
  components: {
    ValidationProvider
  }
})
export default class ValidatedInput extends Vue {
  private innerValue: unknown;

  @Prop()
  readonly value!: unknown;

  @Prop({ type: String })
  readonly vid!: string;

  @Prop({ type: [Object, String] })
  readonly rules!: unknown;

  public created () {
    if (this.value) {
      this.innerValue = this.value
    }
  }

  @Watch('innerValue')
  public innerValueChanged (value: unknown) {
    this.$emit('input', value)
  }

  @Watch('value')
  public valueChanged (value: unknown) {
    this.innerValue = value
  }
}
</script>
